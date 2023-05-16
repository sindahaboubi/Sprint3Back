package com.ms.corbeilleservice.controller;


import java.sql.SQLException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ms.corbeilleservice.entity.TicketTache;
import com.ms.corbeilleservice.model.HistoireTicket;

import com.ms.corbeilleservice.model.SprintBacklog;

import com.ms.corbeilleservice.service.HistoireTicketFeignClient;
import com.ms.corbeilleservice.service.MembreFeignClient;
import com.ms.corbeilleservice.service.SprintBacklogFeignClient;
import com.ms.corbeilleservice.service.TicketTacheFeignClient;

import feign.FeignException;

import com.ms.corbeilleservice.service.CorbeilleService;

@RestController
@RequestMapping("corbeilles")
public class CorbeilleController {
    
    @Autowired
    private CorbeilleService coribeilleService;

    @Autowired
    private MembreFeignClient membreClient;

    @Autowired
    private HistoireTicketFeignClient histoireTicketFeignClient;

    @Autowired
    private SprintBacklogFeignClient sprintBacklogFeignClient;

    @Autowired
    private TicketTacheFeignClient ticketTacheFeignClient;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody 
    public ResponseEntity<TicketTache> ajouterTicketTacheACorbeille(@RequestBody TicketTache tt) {
        tt.setDateLancement(null);
        tt.setDateFin(null);
        TicketTache ttSaved =  coribeilleService.ajouterTicketTache(tt);
        if(ttSaved == null){
            return  new ResponseEntity<>(ttSaved, HttpStatus.BAD_REQUEST);
        }
        if(tt.getSprintBacklogId()!=null){
        SprintBacklog sprintBacklog = this.sprintBacklogFeignClient.getSprintBacklogById(tt.getSprintBacklogId());
        ttSaved.setSprintBacklog(sprintBacklog);
        }
        HistoireTicket ht = this.histoireTicketFeignClient.ticketHistoireById(tt.getTicketHistoireId()); 
        ttSaved.setHt(ht);
        return  new ResponseEntity<>(ttSaved, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>  supprimerTicketTacheDeCorbeille(@PathVariable("id") Long id,@RequestParam("choix") String choix) {

        if(choix.equals("global")){
                boolean isDeleted = coribeilleService.supprimerTicketTache(id);
            if (!isDeleted) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.noContent().build();
        }else{
           
            TicketTache tt = this.coribeilleService.getById(id);
            if(tt != null){
                try{
                if(tt.getSprintBacklogId()!=null){
                tt.setDateLancement(Date.from(Instant.now()));
                tt.setDateFin(Date.from(Instant.now().plus(tt.getNbHeurs(), ChronoUnit.HOURS)));
                }
                 /** verif tache existe deja ou non dans le sprint  */
                this.ticketTacheFeignClient.ajouterTicketTache(tt);
                boolean isDeleted = coribeilleService.supprimerTicketTache(id);
                if (!isDeleted) 
                    return ResponseEntity.notFound().build();
                return ResponseEntity.noContent().build();
                }catch(FeignException e){
                    return ResponseEntity.badRequest().body(null);
                }
            }
            else
                return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> viderCorbeille(){

        long vide = this.coribeilleService.viderCorbeille();
        if (vide > 0 ) 
            return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }
  
    @GetMapping("/membre/{id}")
    public ResponseEntity<List<TicketTache>> getTicketsTacheByMembreId(@PathVariable("id") Long id){
        if(id == null)
            return ResponseEntity.badRequest().body(null);
        else{
            try{
                List<TicketTache> ticketsTache = this.coribeilleService.getTicketTacheByMembreId(id);
                ticketsTache.forEach(ticketTache ->{
                    if(ticketTache.getTicketHistoireId() !=null)
                        ticketTache.setHt(this.histoireTicketFeignClient.ticketHistoireById(ticketTache.getTicketHistoireId())); 
                    if(ticketTache.getSprintBacklogId()!=null)
                        ticketTache.setSprintBacklog(this.sprintBacklogFeignClient.getSprintBacklogById(ticketTache.getSprintBacklogId()));
                    ticketTache.setMembre(this.membreClient.getMembreById(id));
                });
                return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(ticketsTache);
            }catch(SQLException error){
                return ResponseEntity.internalServerError().body(null);
            }  
        }
    }
   

    
}
