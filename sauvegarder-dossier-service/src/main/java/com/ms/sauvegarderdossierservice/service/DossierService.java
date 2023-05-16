package com.ms.sauvegarderdossierservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.sauvegarderdossierservice.entity.Dossier;
import com.ms.sauvegarderdossierservice.repository.DossierRepository;
import java.util.Collections;

@Service
public class DossierService {
    
    @Autowired
    private DossierRepository dossierRepository;

    public Dossier sauvegarderDossier(Dossier dos){

        if(this.dossierRepository.findByMembreIdAndNomDossier(dos.getMembreId(),dos.getNomDossier()).isPresent())
            return null;
        else
            return this.dossierRepository.save(dos);

    }

    public List<Dossier> getDossierByProjet(Long id){
        
        if(this.dossierRepository.findByProjetId(id).isPresent())
            return this.dossierRepository.findByProjetId(id).get();
        return Collections.emptyList();

    }

}
