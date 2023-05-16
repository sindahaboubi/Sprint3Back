package com.ms.chatbotservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.chatbotservice.configuration.OpenaiProperties;
import com.ms.chatbotservice.model.OpenaiRequest;
import com.ms.chatbotservice.model.ResponseOpenAi;

@Service
public class OpenaiGPTService {

    @Autowired
    private OpenaiProperties openaiProperties;

    @Autowired
    private OpenaiClient openaiClient;

    public String genererReponse(String question){

        String modelId = openaiProperties.getModel();
        int maxTokens = openaiProperties.getMaxTokens();
        double temperature = openaiProperties.getTemperature();

        OpenaiRequest requestBody = new OpenaiRequest();
        requestBody.setModel(modelId);
        requestBody.setPrompt(question);
        requestBody.setTemperature(temperature);
        requestBody.setMax_tokens(maxTokens);
        ResponseOpenAi completions = openaiClient.completions("Bearer "+openaiProperties.getApiKey(), requestBody);
        if (completions.getChoices().get(0)!=null ) {
            return completions.getChoices().get(0).getText();
        }
        return "";

        
    }
    
}
