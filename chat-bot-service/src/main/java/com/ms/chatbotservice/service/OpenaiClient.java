package com.ms.chatbotservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ms.chatbotservice.model.OpenaiRequest;
import com.ms.chatbotservice.model.ResponseOpenAi;

import feign.Headers;

@FeignClient(name="openai",url="https://api.openai.com")
public interface OpenaiClient {

    @Headers("Content-Type: application/json")
    @RequestMapping(method = RequestMethod.POST,value="/v1/completions")
    ResponseOpenAi completions(@RequestHeader("Authorization") String authorization,@RequestBody OpenaiRequest requestBody);
    
}
