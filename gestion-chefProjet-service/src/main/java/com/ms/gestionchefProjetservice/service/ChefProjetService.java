package com.ms.gestionchefProjetservice.service;

import java.sql.SQLException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.gestionchefProjetservice.entity.ChefProjet;
import com.ms.gestionchefProjetservice.repository.ChefProjetRepository;
@Service
public class ChefProjetService {
    
    @Autowired
    private ChefProjetRepository chefProjetRepository; 

    
    public ChefProjet getChefProjetById(Long id) throws SQLException{
        return  chefProjetRepository.findById(id).get();
    }

    public ChefProjet getChefProjetByEmail(String email) throws SQLException{
        return chefProjetRepository.findByEmail(email);
    }

    public ChefProjet ajouterChefProjet(ChefProjet chp){
        return this.chefProjetRepository.save(chp);
    }


}
