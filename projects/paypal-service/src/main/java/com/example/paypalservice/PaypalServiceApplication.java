package com.example.paypalservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PaypalServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaypalServiceApplication.class, args);
	}

}
