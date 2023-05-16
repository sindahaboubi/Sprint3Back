package com.ms.notificationservice.service;



import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ms.notificationservice.model.TicketTache;

@FeignClient(name="ticket-tache-service")
public interface TicketTacheFeignClient {

    @GetMapping("/ticket-taches/ticket-histoire/{id-ticket-histoire}")
    List<TicketTache> getTicketTacheByHistoireTicketId(@PathVariable("id-ticket-histoire")Long id);

   
    
}
