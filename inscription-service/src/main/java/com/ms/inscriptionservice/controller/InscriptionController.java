package com.ms.inscriptionservice.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.inscriptionservice.model.ChefProjet;
import com.ms.inscriptionservice.model.Membre;
import com.ms.inscriptionservice.service.InscriptionService;

@RestController
@RequestMapping("/auth")
public class InscriptionController {
     
    @Autowired
    InscriptionService inscriptionService;

    @PostMapping("/chef-projet")
    public ResponseEntity<ChefProjet> inscrirChefProjet(@RequestBody ChefProjet chef) {
        if(chef == null)
            return ResponseEntity.badRequest().body(null);
        else{
            ChefProjet chp = inscriptionService.inscrirChefProjet(chef);
            if(chp == null)
                return ResponseEntity.badRequest().body(null);
            else
                return ResponseEntity.status(201).body(chp);
        }
    }

    @PostMapping("/membre")
    public ResponseEntity<Membre> inscrirMembre(@RequestBody Membre membre) {
        if(membre == null)
            return ResponseEntity.badRequest().body(null);
        if(membre.getId()!=null){
            Membre m = inscriptionService.modifierInscriptionMembre(membre);
            return ResponseEntity.status(200).body(m);
        }else{
            Membre m = inscriptionService.inscrirMembre(membre);
            if(m == null)
                return ResponseEntity.badRequest().body(null);
            else
                return ResponseEntity.status(201).body(m);
        }
    }

    
}
