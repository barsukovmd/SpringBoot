package com.tms.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class Lesson47RestApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(Lesson47RestApiApplication.class, args);
	}

}
