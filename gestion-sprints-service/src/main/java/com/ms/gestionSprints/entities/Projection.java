package com.ms.gestionSprints.entities;

import java.util.Date;

@org.springframework.data.rest.core.config.Projection(name="fullSprint",types=Sprint.class)
public interface Projection {
    
    public Long getId();
    public  Date getDateLancement();
    public  Date getDateFin();
    public String getObjectif();
    public int getVelocite();
    public String getEtat();
    public  ProductBacklog getProductBacklog();

    
}
