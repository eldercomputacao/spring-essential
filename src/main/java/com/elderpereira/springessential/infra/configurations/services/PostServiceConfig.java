package com.elderpereira.springessential.infra.configurations.services;

import com.elderpereira.springessential.domain.ports.PostServicePort;
import com.elderpereira.springessential.domain.service.PostService;
import com.elderpereira.springessential.infra.databases.mongo.repository.PostRepositoryMongoDB;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostServiceConfig {
    @Bean
    public PostServicePort postService(PostRepositoryMongoDB postRepositoryMongoDB) {
        return new PostService(postRepositoryMongoDB);
    }
}
