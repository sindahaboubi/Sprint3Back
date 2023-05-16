package com.ms.chatbotservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class CompletionChoice {
    
    @JsonProperty("text")
    private String text;

    @JsonProperty("index")
    private int index;

    @JsonProperty("logprobs")
    private Object logprobs;

    @JsonProperty("finish_reason")
    private String finishReason;
}
