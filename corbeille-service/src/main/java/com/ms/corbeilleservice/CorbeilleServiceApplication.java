package com.ms.corbeilleservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import com.ms.corbeilleservice.entity.TicketTache;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class CorbeilleServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CorbeilleServiceApplication.class, args);
	}

	
	@Bean
	CommandLineRunner start(RepositoryRestConfiguration configuration ){
		return args->{
			configuration.exposeIdsFor(TicketTache.class);
		};
	}

}
