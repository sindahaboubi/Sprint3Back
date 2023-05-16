package com.ms.corbeilleservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ms.corbeilleservice.model.Membre;

@FeignClient(name="membre-service")
public interface MembreFeignClient {

    @GetMapping("/membres/{id}")
    public Membre getMembreById(@PathVariable("id") Long id); 
    
}
