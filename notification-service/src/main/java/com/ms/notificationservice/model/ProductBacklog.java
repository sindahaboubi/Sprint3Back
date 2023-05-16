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
public class ProductBacklog {

    private int nbrTicket;
    private int velocite;
    private Long id;
    private Date dateCreation;
    private Long  projetId;
    private Projet projet;
}
