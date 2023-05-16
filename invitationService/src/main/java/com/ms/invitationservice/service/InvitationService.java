package com.ms.invitationservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.invitationservice.entity.Invitation;

import com.ms.invitationservice.repository.InvitationRepository;

@Service
public class InvitationService {

    @Autowired
    private InvitationRepository invitationRepository;

    

    public Invitation sauvegarderInvitation(Invitation inv){
            return invitationRepository.save(inv);
    }

    public Invitation getInvitationById(Long id){
        return invitationRepository.findById(id).get();
    } 

    public boolean supprimerInvitaion(Long id){
        if(this.invitationRepository.existsById(id)){
            this.invitationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Invitation> getAllInvitations(){
        return this.invitationRepository.findAll();
    }

   
    
}
