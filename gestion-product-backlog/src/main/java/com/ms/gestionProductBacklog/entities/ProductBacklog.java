package com.ms.gestionProductBacklog.entities;


import com.fasterxml.jackson.annotation.JsonProperty;

import com.ms.gestionProductBacklog.models.Projet;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="product_backlog")
@Data
@ToString
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
public class ProductBacklog extends Backlog{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //L'identifiant du product backlog
    
    @Column(unique = true)
    private Long projetId;
    @Transient
    private Projet projet;
}
