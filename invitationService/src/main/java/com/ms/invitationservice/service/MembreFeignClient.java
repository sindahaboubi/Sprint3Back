package com.ms.invitationservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ms.invitationservice.model.Membre;

import feign.Headers;

@FeignClient(name = "membre-service")
public interface MembreFeignClient {

    @GetMapping("/membres/{id}")
    public Membre getMembreById(@PathVariable Long id) ;
    
    @Headers("Content-Type: application/json")
    @PostMapping("/membres/membre")
    public Membre saveMembre(@RequestBody Membre membre);

}
