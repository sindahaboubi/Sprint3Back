package com.ms.initialiserprojetservice.projections;

import com.ms.initialiserprojetservice.model.Projet;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(name="fullProjet",types= Projet.class)

public interface ProjetProjection {
    public Long getId();
    public String getNom();
    public String getCles();
    public Date getDateDebut();
    public Date getDateFin();
}
