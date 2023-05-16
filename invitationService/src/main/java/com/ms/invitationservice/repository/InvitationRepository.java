package com.ms.invitationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.invitationservice.entity.Invitation;


public interface InvitationRepository extends JpaRepository<Invitation,Long> {
   
}
