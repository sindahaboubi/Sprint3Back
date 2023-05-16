package com.ms.notificationservice.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TicketTache extends Ticket{

    private Long id; 
    private int nbHeurs;
    private Long membreId;
    private Date dateLancement;
    private Date dateFin;
    private Long sprintBacklogId;
    private Long ticketHistoireId;
    private Membre membre; 
    private HistoireTicket ht;
    
}
