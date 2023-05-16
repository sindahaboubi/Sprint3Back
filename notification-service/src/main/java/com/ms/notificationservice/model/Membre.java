package com.ms.notificationservice.model;

import java.util.Date;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Membre {
    
    private Long  id; 
    private String nom ; 
    private String prenom ; 
    private String adresse ; 
    private String telephone; 
    private String email ; 
    private String pwd; 
    private MembreStatus status; 
    private Date dateInscription; 


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Membre membre = (Membre) o;
        return id == membre.getId() && Objects.equals(nom, membre.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom, id);
    }
}
