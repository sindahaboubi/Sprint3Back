package com.ms.invitationservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.invitationservice.entity.Role;
import com.ms.invitationservice.keys.RolePK;
import com.ms.invitationservice.repository.RoleRepository;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;


    public Role sauvegarderRole(Role role){
        if(!roleRepository.existsById(role.getPk()))
            return roleRepository.save(role);
        return null;
    }

    public Role getRoleById(RolePK pk){
        return roleRepository.findById(pk).get();
    } 

    public void supprimerRole(RolePK pk){
        if(this.roleRepository.existsById(pk))
            this.roleRepository.deleteById(pk);
    }
    
    public List<Role>getRoleByProjetId(Long id){
        return this.roleRepository.findByPkProjetId(id);
    }

    public List<Role>getProjetByMembreId(Long id){
        return this.roleRepository.findByPkMembreId(id);
    }

    public Role accepterRole(Role r){
        if(this.roleRepository.existsById(r.getPk())){
          return   this.roleRepository.save(r);
        }
        return null;
    }
}
