package com.codurance.guru;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GuruApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuruApplication.class, args);
	}

}
