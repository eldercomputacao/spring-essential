package com.elderpereira.springessential.infra.configurations.security;

import com.elderpereira.springessential.domain.exceptions.NotFoundException;
import com.elderpereira.springessential.infra.databases.postgres.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return Optional.ofNullable(userJpaRepository.findByUsername(username))
                .orElseThrow(() -> new NotFoundException(String.format("User not found: %s", username)));
    }
}
