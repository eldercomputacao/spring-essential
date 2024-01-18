package com.elderpereira.springessential.infra.configurations.security;

import com.elderpereira.springessential.app.request.AccountCredentialRequest;
import com.elderpereira.springessential.app.response.TokenResponse;
import com.elderpereira.springessential.domain.exceptions.AuthenticationException;
import com.elderpereira.springessential.domain.exceptions.NotFoundException;
import com.elderpereira.springessential.infra.databases.postgres.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserJpaRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public TokenResponse signin(AccountCredentialRequest data) {
        return Optional.ofNullable(repository.findByUsername(data.getUsername()))
                .filter(user -> passwordEncoder.matches(data.getPassword(), user.getPassword()))
                .map(user -> tokenProvider.createAccessToken(user.getUsername(), user.getRoles()))
                .orElseThrow(() -> new AuthenticationException("Invalid credential, check username and password"));
    }

    public TokenResponse refreshToken(String refreshToken) {
        return Optional.ofNullable(refreshToken)
                .filter(token -> tokenProvider.validateToken(token))
                .map(token -> tokenProvider.refreshToken(token))
                .orElseThrow(() -> new AuthenticationException("Invalid token"));
    }
}
