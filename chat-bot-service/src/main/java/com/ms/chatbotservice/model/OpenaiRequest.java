package com.ms.chatbotservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class OpenaiRequest {

    private String model;
    private String prompt; 
    private int max_tokens;
    private double temperature;
    
}
