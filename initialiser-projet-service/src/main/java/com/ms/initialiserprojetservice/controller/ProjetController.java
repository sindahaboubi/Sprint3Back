package com.ms.initialiserprojetservice.controller;

import java.sql.SQLException;
import java.util.List;
import com.ms.initialiserprojetservice.model.Projet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.initialiserprojetservice.service.ChefProjetClientService;
import com.ms.initialiserprojetservice.service.ProjetService;
import com.ms.initialiserprojetservice.model.ChefProjet;

@RestController
@RequestMapping("/projets")
public class ProjetController {
    
    @Autowired
    private ProjetService projetService;


    @Autowired
    private ChefProjetClientService chefProjetClientService;

    @GetMapping
    public List<Projet> getAllProjet() {
        return projetService.findAllProjet();
    }

    @GetMapping("/chefProjets/{id}")
    public ResponseEntity<List<Projet>> getProjetsByChefProjet(@PathVariable(name="id") Long id) {
        
        ChefProjet chef = null;
        List<Projet> projets = null;
        HttpStatus status = HttpStatus.OK;
    
        try {
            chef = this.chefProjetClientService.findChefProjetsById(id);
            projets = this.projetService.findAllProjetByChef(id);
    
            if (chef == null || projets == null) {
                status = HttpStatus.NOT_FOUND;
            } else {
                for (Projet projet : projets) {
                    projet.setChefProjet(chef);
                }
            }
        }catch(SQLException e) {
            e.printStackTrace();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
    
        return new ResponseEntity<>(projets, status);
    }
    

    @PostMapping
    public ResponseEntity<Projet> ajouterProjet(@RequestBody Projet p){
        Projet projet = null;
        HttpStatus status = HttpStatus.CREATED;

    try {
        projet = this.projetService.save(p);

        if (projet == null) {
            status = HttpStatus.BAD_REQUEST;
        }
    } catch (Exception e) {
        e.printStackTrace();
        status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    return new ResponseEntity<Projet>(projet, status);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Projet> getProjetById(@PathVariable Long id){
        Projet projet = null;
        HttpStatus status = HttpStatus.OK;
        try{
            projet = this.projetService.getProjetById(id);
            if (projet == null) {
                status = HttpStatus.BAD_REQUEST;
            }else{
                ChefProjet chef = this.chefProjetClientService.findChefProjetsById(projet.getChefProjetId());
                projet.setChefProjet(chef);
            }
        }catch (Exception e) {
            e.printStackTrace();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Projet>(projet, status);
    }
    
   
    
    
}
