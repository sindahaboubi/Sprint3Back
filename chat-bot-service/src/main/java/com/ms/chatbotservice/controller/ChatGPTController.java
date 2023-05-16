package com.ms.chatbotservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.chatbotservice.entity.Question;
import com.ms.chatbotservice.entity.Reponse;
import com.ms.chatbotservice.service.OpenaiGPTService;
import com.ms.chatbotservice.service.QuestionService;
import com.ms.chatbotservice.service.ReponseService;

@RestController
@RequestMapping("/reponses")
public class ChatGPTController {

    @Autowired
    private OpenaiGPTService openaiService;

    @Autowired
    private ReponseService reponseService;

    @Autowired
    private QuestionService questionService;


    @PostMapping
    public ResponseEntity<Reponse> poseQuestion(@RequestBody Question question){
        Question context = new Question();
        context.setText(question.getText());
        context.setMembreId(question.getMembreId());
        if(this.questionService.verifPersistanceBd()){
            Question questionPrecedent = this.questionService.recuprerDerQuestionPosParMemrbe(question.getMembreId());
            context = this.questionService.reformulerQuestion(questionPrecedent, context);
        }
        
        Question questionInserer = this.questionService.sauvegarderQuestion(question);
        Reponse repErreur = new Reponse();
        repErreur.setText("vous avez déjà envoyer une question paraille");
        if(questionInserer == null)
            return new ResponseEntity<Reponse>(repErreur, HttpStatus.BAD_REQUEST);
        
        String reponseBot = openaiService.genererReponse(context.getText());
        /** modifier la question dans la base apres sa gestion par l'api openai */

        /** end */
        Reponse rep = this.reponseService.sauvegarderReponse(reponseBot, question);
        String repResult = rep.getText();
        if(repResult.contains("Réponse :"))
             repResult = rep.getText().replace("Réponse :","");
        rep.setText(repResult);
        return ResponseEntity.ok().body(rep);

    }

    @GetMapping
    public ResponseEntity<List<Reponse>> getReponseParMembre(@RequestParam("membreId") String membreId) throws JsonMappingException, JsonProcessingException{
        Long idMembre = new ObjectMapper().readValue(membreId,Long.class);
        if(idMembre ==null)
            return ResponseEntity.badRequest().body(null);
        List<Reponse> reps = this.reponseService.getReponseParMembreId(idMembre);
        return ResponseEntity.ok().body(reps);
    }
    
}
