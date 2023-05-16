package com.ms.notificationservice.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Sprint {

    private Long id;
    private Date dateLancement;
    private Date dateFin;
    private String objectif;
    private int velocite;
    private String etat;
    private Long productBacklogId;
    private ProductBacklog productBacklog;


    public boolean comparerFinAujourdhui(){
        Date ajourdhui = new Date();
        return ajourdhui.getYear() == dateFin.getYear() &&
        ajourdhui.getMonth() == dateFin.getMonth() &&
        ajourdhui.getDate() == dateFin.getDate() &&
        ajourdhui.getHours() > 12 ;

    }
    
}
