package com.ms.corbeilleservice.service;

import java.sql.SQLException;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.ms.corbeilleservice.entity.TicketTache;
import com.ms.corbeilleservice.repository.CorbeilleRepository;

@Service
public class CorbeilleService {
    
    @Autowired
    private CorbeilleRepository corbeilleRepository;

    public TicketTache ajouterTicketTache(TicketTache tt){
            return this.corbeilleRepository.save(tt);
    } 
    
    public TicketTache getById(Long id){
        Optional<TicketTache> ticketTache = this.corbeilleRepository.findById(id);
        if (ticketTache.isPresent())
            return ticketTache.get();
        else
            return null;
    }

    public boolean supprimerTicketTache(Long id){
        Optional<TicketTache> ticketTache = this.corbeilleRepository.findById(id);
        if (ticketTache.isPresent()) {
        try {
            this.corbeilleRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException ex) {
            ex.printStackTrace();
            return false;
        }
      }return false;
    }

    public List<TicketTache> getTicketTacheByMembreId(Long id) throws SQLException {
        if(id == null)
            return null;
        else 
            return this.corbeilleRepository.findByMembreId(id);
    }

    public long viderCorbeille(){
        this.corbeilleRepository.deleteAll();
        return this.corbeilleRepository.count();
    }
}
