package com.elderpereira.springessential;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication
public class SpringbootEssentialApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootEssentialApplication.class, args);
	}

}
