package com.ms.notificationservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ms.notificationservice.model.Membre;

@FeignClient(name="membre-service")
public interface MembreFeignClient {

    @GetMapping("/membres/{id}")
    public Membre findMembreById(@PathVariable("id") Long id);
    
}
