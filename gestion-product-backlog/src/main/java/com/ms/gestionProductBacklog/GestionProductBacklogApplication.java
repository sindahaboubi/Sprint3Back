package com.ms.gestionProductBacklog;

import com.ms.gestionProductBacklog.entities.ProductBacklog;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class GestionProductBacklogApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionProductBacklogApplication.class, args);
	}
	@Bean
	CommandLineRunner start(RepositoryRestConfiguration configuration ){
		return args ->{
			configuration.exposeIdsFor(ProductBacklog.class);
		};
	}
	@Configuration
	public class RestTemplateConfig {
		@Bean
		public RestTemplate restTemplate(RestTemplateBuilder builder) {
			return builder.build();
		}
	}
}
