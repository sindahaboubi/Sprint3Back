package com.ms.invitationservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.invitationservice.entity.Role;
import com.ms.invitationservice.keys.RolePK;

public interface RoleRepository extends JpaRepository<Role,RolePK> {
    
    List<Role> findByPkProjetId(Long projetId);
    List<Role> findByPkMembreId(Long projetId);
}
