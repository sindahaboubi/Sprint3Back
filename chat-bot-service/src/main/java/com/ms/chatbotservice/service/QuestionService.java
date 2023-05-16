package com.ms.chatbotservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.chatbotservice.entity.Question;
import com.ms.chatbotservice.entity.Reponse;
import com.ms.chatbotservice.repository.QuestionRepository;
import com.ms.chatbotservice.repository.ReponseRepository;


@Service
public class QuestionService {
    
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ReponseRepository responseRepository;

    public Question sauvegarderQuestion(Question q){
        List<Question> questions = this.questionRepository.findAll();
        for(Question question : questions){
            if(q.equals(question))
                return null;
        }
        return questionRepository.save(q);
    }
    
    public Question recuprerDerQuestionPosParMemrbe(Long membreId){
        return this.questionRepository.findFirstByMembreIdOrderByIdDesc(membreId);
    }

    public Question reformulerQuestion(Question questionPrecedente,Question questionActuelle){
          
        if(questionPrecedente!=null){
            Reponse repPrecedente  = this.responseRepository.findFirstByOrderByIdDesc();
            System.out.println("++++++"+repPrecedente);
            questionActuelle.setText(
                "/'reponse precedente : /'"
                +repPrecedente.getText()
                +"/' maintenant ma question: /'"
                +questionActuelle.getText()+"/'"
            );
        }
        return questionActuelle ;
    }

    public boolean verifPersistanceBd(){
        return this.questionRepository.count()>0;
    }

}
