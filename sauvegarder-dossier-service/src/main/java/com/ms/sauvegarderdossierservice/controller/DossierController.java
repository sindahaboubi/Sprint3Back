package com.ms.sauvegarderdossierservice.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ms.sauvegarderdossierservice.entity.Dossier;
import com.ms.sauvegarderdossierservice.model.Membre;
import com.ms.sauvegarderdossierservice.service.DossierService;
import com.ms.sauvegarderdossierservice.service.MembreFeignClient;

@RestController
@RequestMapping("/dossiers")
public class DossierController {
    
    @Autowired
    private DossierService dossierService;

    @Autowired
    private MembreFeignClient membreClient;

    @PostMapping
    public ResponseEntity<Dossier> sauvegarderDossier(
    @RequestBody MultipartFile file, 
    @RequestParam("nomDossier") String nom,
    @RequestParam("projetId") Long projetId, 
    @RequestParam("membreId") Long membreId) throws IOException{

    Dossier dossier = new Dossier();
    dossier.setNomDossier(nom);
    dossier.setProjetId(projetId);
    dossier.setMembreId(membreId);
    dossier.setDonnees(file.getBytes());

    Dossier dosSaved = this.dossierService.sauvegarderDossier(dossier);
    if(dosSaved == null)
        return ResponseEntity.badRequest().body(null);
    else
        if(dosSaved.getMembreId()!=null){
            Membre membre = this.membreClient.getMembre(dosSaved.getMembreId());
            dosSaved.setMembre(membre);
        }
        return ResponseEntity.status(201).body(dosSaved);

    }



    @GetMapping("/projet/{id}")
    public ResponseEntity<List<Dossier>> getDossierByProjetId(@PathVariable("id") Long id){

        List<Dossier> projetDos = this.dossierService.getDossierByProjet(id);

        if(projetDos.isEmpty())
            return ResponseEntity.status(404).body(Collections.emptyList());
        else{
            for(Dossier dos:projetDos){
                Membre membre = this.membreClient.getMembre(dos.getMembreId());
                dos.setMembre(membre);
            }
            return ResponseEntity.ok().body(projetDos);
        }
        
    }

}
