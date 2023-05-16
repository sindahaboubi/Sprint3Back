package com.ms.sauvegarderdossierservice.entity;

import com.ms.sauvegarderdossierservice.model.Membre;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Transient;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@Entity
public class Dossier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    
  
    @Lob
    private byte[] donnees;


    private String nomDossier ;
    @Column(name="membre_id")
    private Long membreId;
    @Column(name="projet_id")
    private Long projetId; 

    @Transient
    private Membre membre ; 

    
}
