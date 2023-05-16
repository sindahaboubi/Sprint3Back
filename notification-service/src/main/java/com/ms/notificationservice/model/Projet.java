package com.ms.notificationservice.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

