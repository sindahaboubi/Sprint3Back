package com.ms.invitationservice.controller;

import java.util.List;

import org.apache.catalina.mbeans.RoleMBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.invitationservice.entity.Role;
import com.ms.invitationservice.exception.MembreNotFoundException;
import com.ms.invitationservice.exception.ProjetNotFoundException;
import com.ms.invitationservice.exception.RoleNotFoundException;
import com.ms.invitationservice.keys.RolePK;
import com.ms.invitationservice.model.ChefProjet;
import com.ms.invitationservice.model.Membre;
import com.ms.invitationservice.model.Projet;
import com.ms.invitationservice.service.ChefProjetFeignClient;
import com.ms.invitationservice.service.MembreFeignClient;
import com.ms.invitationservice.service.ProjetFeignClient;
import com.ms.invitationservice.service.RoleService;

@RestController
@RequestMapping("/roles")
public class RoleController {
    
    @Autowired
    private RoleService roleService;

    @Autowired
    private ProjetFeignClient projetClient ;

    @Autowired
    private ChefProjetFeignClient chefProjetFeignClient;

    @Autowired
    private MembreFeignClient membreClient ;

    @Autowired
    private ProjetFeignClient projetFeignClient ;

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping
    public ResponseEntity<Role> sauvegarderRole(@RequestBody Role role) {
           
            Projet projet = projetClient.getProjetById(role.getPk().getProjetId());
            Membre membre = membreClient.getMembreById(role.getPk().getMembreId());
            HttpStatus status = HttpStatus.CREATED;
            if (projet == null) 
                return ResponseEntity.badRequest().body(null);
            
            if (membre == null) 
                return ResponseEntity.badRequest().body(null);
            
            role.setProjet(projet);
            role.setMembre(membre);
            roleService.sauvegarderRole(role);
            return new ResponseEntity<Role>(role, status);
        } 

    

    @PostMapping("/role")
    public ResponseEntity<Role> getInvitationById(@RequestBody RolePK pk) {
        try {
            Projet projet = projetClient.getProjetById(pk.getProjetId());
            if (projet == null) {
                throw new ProjetNotFoundException("Le projet avec l'ID " + pk.getProjetId() + " n\'existe pas.");
            }
            Role r = roleService.getRoleById(pk);
            r.setProjet(projet);
            return ResponseEntity.ok(r);
        } catch (ProjetNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (MembreNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @DeleteMapping
    public ResponseEntity<?> supprimerInvitaion(@RequestParam("rolePk") String pkString,@RequestParam("idchef") String chefId) throws JsonMappingException, MessagingException, JsonProcessingException {
        RolePK pk = new ObjectMapper().readValue(pkString, RolePK.class);
        
        Long chefProjetId = new ObjectMapper().readValue(chefId, Long.class);
        
        ChefProjet chp = chefProjetFeignClient.getChefProjetsById(chefProjetId);
        
        Membre membre = this.membreClient.getMembreById(pk.getMembreId());
        Projet p = this.projetFeignClient.getProjetById(pk.getProjetId());

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(membre.getEmail());
        helper.setSubject("Invitation au projet " + p.getNom());
        helper.setTo(chp.getEmail());
        String html = "<html><head><style>" +
                "body { font-family: Arial, sans-serif; font-size: 16px; color: #333; background-color: #fff; margin: 0; }" +
                "h1 { font-weight: bold; font-size: 18px; }" +
                "p { margin: 10px 0; }" +
                ".btn { display: inline-block; padding: 10px 20px; background-color: #d9534f; color: #fff; text-decoration: none; }" +
                ".btn:hover { background-color: #c9302c; }" +
                "</style></head><body>" +
                "<h1>Refus d'invitation au projet " + p.getNom() + "</h1>" +
                "<p>Bonjour,</p>" +
                "<p>Nous sommes désolés de vous informer que votre invitation à rejoindre le projet " + p.getNom() + " a été refusée par le membre " + membre.getEmail() + ".</p>" +
                "<p>Si vous avez des questions ou des préoccupations, veuillez contacter le membre directement.</p>" +
                "<p>Cordialement," +
                "<p><a class='btn' href='http://localhost:4200/'>Retourner à la page d'accueil</a></p>" +
                "</body></html>";

        helper.setText(html, true);
        mailSender.send(message);
        try {
            roleService.supprimerRole(pk);
            return ResponseEntity.noContent().build();
        } catch (RoleNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Le rôle avec l'ID " + pk.toString() + " n'a pas été trouvé.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur est survenue lors de la suppression du rôle avec l'ID " + pk.toString());
        }
    }

    @GetMapping("/projets/{idProjet}")
    public ResponseEntity<List<Role>> getRolesByProjetId(@PathVariable("idProjet") Long id) {

       
        List<Role> listeRoles = this.roleService.getRoleByProjetId(id);
        for(Role r:listeRoles){
            try {
                Projet projet = projetClient.getProjetById(r.getPk().getProjetId());
                if (projet == null) {
                    throw new ProjetNotFoundException("Le projet avec l'ID " + r.getPk().getProjetId() + " n'existe pas.");
                }
                Membre m = this.membreClient.getMembreById(r.getPk().getMembreId());
                
                if (m == null) {
                    throw new MembreNotFoundException("Le membre avec l'ID " + r.getPk().getMembreId() + " n'existe pas.");
                }
                r.setMembre(m);
                r.setProjet(projet);
            } catch (ProjetNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } catch (MembreNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(listeRoles);
    }

    @PutMapping
    public ResponseEntity<Object> accepterRole(@RequestBody Role r){
        if(r == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Role roleModifie = this.roleService.accepterRole(r);
        return ResponseEntity.status(HttpStatus.OK).body(roleModifie);
    }


    @GetMapping("/membres/{idMembre}")
    public ResponseEntity<List<Role>> getRolesByMembrbeId(@PathVariable("idMembre") Long id) {

       
        List<Role> listeRoles = this.roleService.getProjetByMembreId(id);
        for(Role r:listeRoles){
            try {
                Projet projet = projetClient.getProjetById(r.getPk().getProjetId());
                if (projet == null) {
                    throw new ProjetNotFoundException("Le projet avec l'ID " + r.getPk().getProjetId() + " n'existe pas.");
                }
                Membre m = this.membreClient.getMembreById(r.getPk().getMembreId());
                
                if (m == null) {
                    throw new MembreNotFoundException("Le membre avec l'ID " + r.getPk().getMembreId() + " n'existe pas.");
                }
                r.setMembre(m);
                r.setProjet(projet);
            } catch (ProjetNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } catch (MembreNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(listeRoles);
    }



    
}
