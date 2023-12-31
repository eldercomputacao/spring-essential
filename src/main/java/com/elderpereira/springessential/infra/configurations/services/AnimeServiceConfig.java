package com.elderpereira.springessential.infra.configurations.services;

import com.elderpereira.springessential.domain.ports.AnimeServicePort;
import com.elderpereira.springessential.domain.service.AnimeService;
import com.elderpereira.springessential.infra.databases.postgres.repository.AnimeRepositoryPostgres;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnimeServiceConfig {
    @Bean
    public AnimeServicePort animeService(AnimeRepositoryPostgres animeRepositoryPostgres) {
        return new AnimeService(animeRepositoryPostgres);
    }
}
