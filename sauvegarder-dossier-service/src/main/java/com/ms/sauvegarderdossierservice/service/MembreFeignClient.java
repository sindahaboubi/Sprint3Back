package com.ms.sauvegarderdossierservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ms.sauvegarderdossierservice.model.Membre;

@FeignClient(name="membre-service")
public interface MembreFeignClient {
    
    @GetMapping("/membres/{id}")
    public Membre getMembre(@PathVariable("id") Long id);
}
