package com.ms.corbeilleservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ms.corbeilleservice.model.HistoireTicket;

@FeignClient(name="gestion-histoire-ticket")
public interface HistoireTicketFeignClient {
    
    @GetMapping("/histoireTickets/{id}")
    public HistoireTicket ticketHistoireById(@PathVariable("id") Long id);

}
