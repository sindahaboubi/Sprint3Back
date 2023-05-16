package com.ms.inscriptionservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.ms.inscriptionservice.model.Membre;

@FeignClient(name="membre-service")
public interface MembreFeignClient {

    @GetMapping("/membres/membre")
    Membre getMembreByEmail(@RequestParam("email") String email);

    @PostMapping("/membres/membre")
    Membre ajouterMembre(@RequestBody Membre m);

    @PutMapping("/membres")
    Membre modifierMembre(@RequestBody Membre m);

    
}
