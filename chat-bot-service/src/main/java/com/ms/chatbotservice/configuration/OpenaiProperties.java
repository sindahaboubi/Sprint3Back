package com.ms.chatbotservice.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix="openai")
public class OpenaiProperties {

    private String  apiKey = "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" ;
    private String model = "text-davinci-003";
    private int maxTokens =2600;
    private int temperature = 0;
    

}
