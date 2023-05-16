package com.ms.corbeilleservice.model;



import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class Ticket {

    @Column(name="titre")
    private String titre; //Le titre du ticket
    private String description; //La description du ticket

    
   
}
