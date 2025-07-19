package com.example.reclamation_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient

@SpringBootApplication
public class ReclamationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReclamationServiceApplication.class, args);
	}

}
