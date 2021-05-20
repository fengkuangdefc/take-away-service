package com.chenkesi.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TakeAwayOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(TakeAwayOrderApplication.class, args);
	}

}
