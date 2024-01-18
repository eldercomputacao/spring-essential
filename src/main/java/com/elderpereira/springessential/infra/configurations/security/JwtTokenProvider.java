package com.elderpereira.springessential.infra.configurations.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.elderpereira.springessential.app.response.TokenResponse;
import com.elderpereira.springessential.domain.exceptions.AuthenticationException;
import com.elderpereira.springessential.domain.exceptions.InvalidJwtAuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class JwtTokenProvider {
    private final Integer TOKEN_INDEX = 1;
    private final String EMPTY_SPACE = " ";

    @Value("${security.jwt.token.secret-key:secret}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length:3600000}")
    private long validityInMilliseconds; // 1h

    @Autowired
    private UserDetailsService userDetailsService;

    public TokenResponse createAccessToken(String username, List<String> roles) {
        try {
            var nowDate = new Date();
            var expirationDate = new Date(nowDate.getTime() + validityInMilliseconds);
            var accessToken = getAccessToken(username, roles, nowDate, expirationDate);
            var refreshToken = getRefreshToken(username, roles, nowDate);

            return TokenResponse.builder()
                    .username(username)
                    .authenticated(true)
                    .created(nowDate)
                    .expiration(expirationDate)
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();

        } catch (Exception ex) {
            throw new AuthenticationException("Access token was not created");
        }
    }

    public TokenResponse refreshToken(String refreshToken) {
        try {
            refreshToken = extractToken(refreshToken);
            JWTVerifier jwtVerifier = JWT.require(signAlgorithmHMAC256()).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(refreshToken);

            if (decodedJWT.getExpiresAt().before(new Date()))
                throw new IllegalArgumentException("Inspired token");

            String username = decodedJWT.getSubject();
            List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
            return createAccessToken(username, roles);
        } catch (Exception e) {
            throw new AuthenticationException("Access token is invalid");
        }
    }

    public boolean validateToken(String token) {
        DecodedJWT decodedJWT = decodedToken(token);
        return decodedJWT.getExpiresAt().after(new Date());
    }

    public Authentication getAuthentication(String token) {
        try {
            DecodedJWT decodedJWT = decodedToken(token);
            UserDetails userDetails = this.userDetailsService
                    .loadUserByUsername(decodedJWT.getSubject());
            return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        } catch (Exception e) {
            throw new InvalidJwtAuthenticationException("Invalid JWT token!");
        }
    }

    public String resolveToken(HttpServletRequest req) {
        try {
            return extractToken(req.getHeader("Authorization"));
        } catch (Exception ex) {
            return null;
        }
    }

    private Algorithm signAlgorithmHMAC256() {
        String secretKeyEncoder = Base64.getEncoder().encodeToString(secretKey.getBytes());
        return Algorithm.HMAC256(secretKeyEncoder.getBytes());
    }

    private String getAccessToken(String username, List<String> roles, Date nowDate, Date expirationDate) {
        String issuerUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath().build().toUriString();
        return JWT.create()
                .withSubject(username)
                .withClaim("roles", roles)
                .withIssuedAt(nowDate)
                .withExpiresAt(expirationDate)
                .withIssuer(issuerUrl)
                .sign(signAlgorithmHMAC256())
                .strip();
    }

    private String getRefreshToken(String username, List<String> roles, Date nowDate) {
        var expirationDateRefreshToken = new Date(nowDate.getTime() + (validityInMilliseconds * 3));
        return JWT.create()
                .withSubject(username)
                .withClaim("roles", roles)
                .withIssuedAt(nowDate)
                .withExpiresAt(expirationDateRefreshToken)
                .sign(signAlgorithmHMAC256())
                .strip();
    }

    private DecodedJWT decodedToken(String token) {
        try {
            token = extractToken(token);
            JWTVerifier jwtVerifier = JWT.require(signAlgorithmHMAC256()).build();
            return jwtVerifier.verify(token);
        } catch (Exception ex) {
            throw new AuthenticationException("Invalid JWT token");
        }
    }

    private String extractToken(String token) {
        if (isEmpty(token)) {
            throw new AuthenticationException("The access token was not informed");
        }
        if (token.contains(EMPTY_SPACE)) {
            return token.split(EMPTY_SPACE)[TOKEN_INDEX];
        }
        return token;
    }

}
