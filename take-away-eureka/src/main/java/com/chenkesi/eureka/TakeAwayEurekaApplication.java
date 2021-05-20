package com.chenkesi.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class TakeAwayEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TakeAwayEurekaApplication.class, args);
	}

}
