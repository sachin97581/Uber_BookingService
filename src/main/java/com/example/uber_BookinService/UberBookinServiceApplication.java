package com.example.uber_BookinService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EntityScan("com.example.uberProject_EntityService.model")
public class UberBookinServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UberBookinServiceApplication.class, args);
	}

}
