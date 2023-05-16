package com.ms.chatbotservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.chatbotservice.entity.Question;

public interface QuestionRepository extends JpaRepository<Question,Long> {

    Question findFirstByMembreIdOrderByIdDesc(Long membreId);
    
}
