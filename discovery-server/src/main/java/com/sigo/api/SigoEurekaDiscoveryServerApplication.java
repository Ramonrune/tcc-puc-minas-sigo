package com.sigo.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SigoEurekaDiscoveryServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SigoEurekaDiscoveryServerApplication.class, args);
	}

}
