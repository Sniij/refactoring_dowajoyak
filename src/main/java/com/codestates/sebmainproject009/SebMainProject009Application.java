package com.codestates.sebmainproject009;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SebMainProject009Application {

	public static void main(String[] args) {



		SpringApplication.run(SebMainProject009Application.class, args);
	}

}
