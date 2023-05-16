package com.ms.gestionchefProjetservice.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ms.gestionchefProjetservice.entity.ChefProjet;
import com.ms.gestionchefProjetservice.service.ChefProjetService;

@RestController
@RequestMapping("/chef-projets")
public class ChefProjetController {
    
    @Autowired
    private ChefProjetService chefProjetService;


    @GetMapping("/{id}")
    public ResponseEntity<ChefProjet> getChefProjetById(@PathVariable(name="id") Long id){

        ChefProjet chefProjet = null;
        HttpStatus status = HttpStatus.OK;

    try {
        chefProjet = this.chefProjetService.getChefProjetById(id);

        if (chefProjet == null || id == null) {
            status = HttpStatus.NOT_FOUND;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    return new ResponseEntity<>(chefProjet, status);
    }


    @GetMapping
    public ChefProjet getChefProjetByEmail(@RequestParam("email") String email){
        try {
            return this.chefProjetService.getChefProjetByEmail(email);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping
    public ChefProjet ajoutChefProjet(@RequestBody ChefProjet chp){
        if(chp==null)
            return null;
        return this.chefProjetService.ajouterChefProjet(chp);
    }



}
