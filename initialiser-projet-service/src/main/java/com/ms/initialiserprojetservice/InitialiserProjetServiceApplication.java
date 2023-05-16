package com.ms.initialiserprojetservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import com.ms.initialiserprojetservice.model.Projet;

@SpringBootApplication
@EnableFeignClients
public class InitialiserProjetServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InitialiserProjetServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(RepositoryRestConfiguration configuration ){
		return args->{
			configuration.exposeIdsFor(Projet.class);
		};
	}

}
