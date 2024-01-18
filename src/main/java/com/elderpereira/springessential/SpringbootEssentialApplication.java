package com.elderpereira.springessential;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Log4j2
@EnableMongoRepositories
@SpringBootApplication
public class SpringbootEssentialApplication implements CommandLineRunner {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(SpringbootEssentialApplication.class, args);
    }

    // TODO run method used to generate password
    @Override
    public void run(String... args) throws Exception {
        String generatePassword = passwordEncoder.encode("elder123");
        log.warn(String.format("password: |%s|", generatePassword));
    }
}
