package com.spring.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.spring.security")
public class SecurityBecoderApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityBecoderApplication.class, args);
	}

}
