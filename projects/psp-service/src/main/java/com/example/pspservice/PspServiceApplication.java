package com.example.pspservice;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
@EnableEurekaClient
@EnableEncryptableProperties
public class PspServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PspServiceApplication.class, args);
	}

}
