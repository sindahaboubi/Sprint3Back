package com.ms.corbeilleservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ms.corbeilleservice.entity.TicketTache;

@FeignClient(name="ticket-tache-service")
public interface TicketTacheFeignClient {

    @PostMapping("/ticket-taches")
    public TicketTache ajouterTicketTache(@RequestBody TicketTache ticketTache);
    
}
