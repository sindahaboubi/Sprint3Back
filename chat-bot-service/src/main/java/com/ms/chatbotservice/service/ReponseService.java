package com.ms.chatbotservice.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.chatbotservice.entity.Question;
import com.ms.chatbotservice.entity.Reponse;
import com.ms.chatbotservice.repository.ReponseRepository;


@Service
public class ReponseService {
    
    @Autowired
    private ReponseRepository reponseRepository;


    public Reponse sauvegarderReponse(String reponseText,Question q){

        Reponse rep = new Reponse();
        if(reponseText.equals(""))
            rep.setText("pas de reponse pour cette question Desolé 😅");
        else
            rep.setText(reponseText);
        rep.setQuestion(q);
        return this.reponseRepository.save(rep);

    }



    public List<Reponse> getReponseParMembreId(Long id){
        return this.reponseRepository.findByQuestionMembreId(id);
    }
}
