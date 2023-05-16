package com.ms.gestionProductBacklog.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Projet {
    private Long id;
    private String nom;
    private String cles;
    private Date dateDebut;
    private Date dateFin;
}
