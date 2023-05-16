package com.ms.corbeilleservice.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.corbeilleservice.entity.TicketTache;

public interface CorbeilleRepository extends JpaRepository<TicketTache,Long> {
    
    List<TicketTache> findByMembreId(Long id);
    
}
