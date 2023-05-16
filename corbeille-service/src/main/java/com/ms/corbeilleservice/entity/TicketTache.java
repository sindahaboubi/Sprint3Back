package com.ms.corbeilleservice.entity;

import java.util.Date;

import com.ms.corbeilleservice.model.HistoireTicket;
import com.ms.corbeilleservice.model.Membre;
import com.ms.corbeilleservice.model.SprintBacklog;
import com.ms.corbeilleservice.model.Ticket;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name="ticket_tache_supprimer")
public class TicketTache extends Ticket {

    @Id
    private Long id; 

    @Column(name="nbr_heurs")
    private int nbHeurs;

    @Column(name="membre_id")
    private Long membreId;

    @Column(name="date_lancement")
    private Date dateLancement;

    @Column(name="date_fin")
    private Date dateFin;

    @Column(name="sprint_backlog_id")
    private Long sprintBacklogId;

    @Column(name="ticket_histoire_id")
    private Long ticketHistoireId;

    private String etat;//  en cours .. a verifier .. a faire .. termine

    @Transient
    private Membre membre; 

    @Transient
    private HistoireTicket ht;

    @Transient
    private SprintBacklog sprintBacklog;
    

    
}
