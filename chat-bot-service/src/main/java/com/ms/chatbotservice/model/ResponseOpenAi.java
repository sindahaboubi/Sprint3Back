package com.ms.chatbotservice.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ResponseOpenAi {

        @JsonProperty("id")
        private String id;
    
        @JsonProperty("object")
        private String object;
    
        @JsonProperty("created")
        private long created;
    
        @JsonProperty("model")
        private String model;
    
        @JsonProperty("choices")
        private List<CompletionChoice> choices;
    
        @JsonProperty("usage")
        private Usage usage;
    
}
