package com.ms.chatbotservice.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.chatbotservice.entity.Reponse;

public interface ReponseRepository extends JpaRepository<Reponse,Long>{
    
   Reponse findFirstByOrderByIdDesc();
   List<Reponse> findByQuestionMembreId(Long membreId);
}
