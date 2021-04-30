package com.transtour.backend.travel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

//@EnableAutoConfiguration
@SpringBootApplication
//@EnableSwagger2
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableFeignClients
@EnableMongoRepositories(basePackages = "com.transtour.backend.travel.repository")
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}
