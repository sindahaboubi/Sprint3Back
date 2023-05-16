package com.ms.gestionSprints;

import com.ms.gestionSprints.entities.Sprint;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
@EnableFeignClients
public class GestionSprintsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionSprintsServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(RepositoryRestConfiguration configuration){
		return args ->{
			configuration.exposeIdsFor(Sprint.class);
		};
	}

}
