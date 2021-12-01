package com.example.testservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class TestServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestServiceApplication.class, args);
	}

}
