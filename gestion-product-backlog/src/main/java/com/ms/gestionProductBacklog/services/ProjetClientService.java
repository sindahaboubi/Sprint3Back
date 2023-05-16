package com.ms.gestionProductBacklog.services;

import com.ms.gestionProductBacklog.models.Projet;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@FeignClient(name="initialiser-projet-service")
public interface ProjetClientService {
    @GetMapping("/projets/{id}?projection=fullDetails")
    public Projet findProjetById(@PathVariable("id") Long id);
}
