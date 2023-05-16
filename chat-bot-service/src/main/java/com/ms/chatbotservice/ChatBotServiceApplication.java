package com.ms.chatbotservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import com.ms.chatbotservice.entity.Question;
import com.ms.chatbotservice.entity.Reponse;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ChatBotServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatBotServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(RepositoryRestConfiguration configuration){
		return args ->{
			configuration.exposeIdsFor(Reponse.class);
			configuration.exposeIdsFor(Question.class);
		};
	}

}
