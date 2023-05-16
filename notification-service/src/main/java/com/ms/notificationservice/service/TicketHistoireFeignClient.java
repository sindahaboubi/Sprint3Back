package com.ms.notificationservice.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ms.notificationservice.model.HistoireTicket;

@FeignClient(name="gestion-histoire-ticket")
public interface TicketHistoireFeignClient {
    
    @GetMapping("/histoireTickets/sprint/{id-sprint}")
    public List<HistoireTicket> ticketHistoireBySprintId(@PathVariable("id-sprint") Long id);
}
