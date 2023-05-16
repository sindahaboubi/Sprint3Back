package com.ms.invitationservice.entity;





import java.util.Date;

import com.ms.invitationservice.model.ChefProjet;
import com.ms.invitationservice.model.Membre;

import jakarta.persistence.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "chef_projet_id")
    private Long chefProjetId; 
    // private Long chefProjetId; //identfiant chef projet
    
    @Column(nullable = true,name = "membre_id")
    private Long membreId; //identfiant membre peut etre null si le membre est non existant

    @Transient
    private Membre membre;
    
    @Transient
    private ChefProjet chefProjet; 
    

    @Column(nullable = true)
    private String emailInvitee;// peut etre null si le membre existe dans la base

    @Column(name="date_expiration")
    private Date dateExpiration;//date d'expiration de l'email
    
    
    

 


    
}
