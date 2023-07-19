package com.sparta.outsideworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class OutsideworldApplication {
	public static void main(String[] args) {
		SpringApplication.run(OutsideworldApplication.class, args);
	}

}
