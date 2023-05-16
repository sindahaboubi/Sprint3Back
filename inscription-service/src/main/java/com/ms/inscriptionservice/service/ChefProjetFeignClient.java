package com.ms.inscriptionservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.ms.inscriptionservice.model.ChefProjet;

@FeignClient(name="gestion-chefProjet-service")
public interface ChefProjetFeignClient {

    @GetMapping("/chef-projets")
    ChefProjet getChefProjetByEmail(@RequestParam("email") String email);

    @PostMapping("/chef-projets")
    ChefProjet ajouterChefProjet(@RequestBody ChefProjet chp);
    
}
