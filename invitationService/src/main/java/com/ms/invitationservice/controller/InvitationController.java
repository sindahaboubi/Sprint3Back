package com.ms.invitationservice.controller;

import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import com.ms.invitationservice.entity.Invitation;

import com.ms.invitationservice.model.ChefProjet;
import com.ms.invitationservice.model.Membre;
import com.ms.invitationservice.model.Projet;
import com.ms.invitationservice.service.ChefProjetFeignClient;
import com.ms.invitationservice.service.InvitationService;
import com.ms.invitationservice.service.MembreFeignClient;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@RestController
@RequestMapping("/invitations")
public class InvitationController {

    @Autowired
    private InvitationService invitationService;

    @Autowired
    private MembreFeignClient membreFeignClient;

    @Autowired
    private ChefProjetFeignClient chefProjetFeignClient;

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/invitation")
    public Invitation sauvegarderInvitation(@RequestBody InvitationProjetRequest request) throws MessagingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalArgumentException,  InvalidParameterSpecException, IllegalBlockSizeException, BadPaddingException {

        Invitation inv = request.getInvitation();
        Projet p = request.getProjet();
        ChefProjet chp = chefProjetFeignClient.getChefProjetsById(request.getInvitation().getChefProjetId());
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(chp.getEmail());
        helper.setSubject("Invitation au projet " + p.getNom());
        if (inv.getMembreId() != null) {
            Membre membre = membreFeignClient.getMembreById(inv.getMembreId());
            inv.setMembre(membre);
            inv.setMembreId(membre.getId());
            helper.setTo(membre.getEmail());
            String html = "<html><body><p>Bonjour,</p><p>Vous êtes invité à rejoindre le projet " + p.getNom()
                    + ".</p>Verifie votre compte Numeryx Scrum <p></body></html>";
            helper.setText(html, true);
        } else {
            // Remplacer avec le lien de votre application
           
            Membre creerMembre = new Membre();
            creerMembre.setEmail(inv.getEmailInvitee());
            Membre membre = membreFeignClient.saveMembre(creerMembre);

             /** bloc de generation  token qui contient la date de l'expiration de l'invitation */
             Instant expirationDate = Instant.now().plus(1, ChronoUnit.DAYS);
             String token = JWT.create()
                 .withExpiresAt(Date.from(expirationDate))
                 .withIssuer("Numeryx Scrum")
                 .withClaim("chefProjetId", chp.getId())
                 .sign(Algorithm.HMAC256("cleanCode"));
             String link = String.format("http://localhost:4200/#/decision/%d/membres/%d/%s", p.getId(), membre.getId(), token);
             /** end bloc token  */

              /** bloc de chifferement de lien */
              KeyGenerator keyGen = KeyGenerator.getInstance("AES");
              keyGen.init(128);
              SecretKey secretKey = keyGen.generateKey();
              /** chiffrement symetrqiue  */
              Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
              cipher.init(Cipher.ENCRYPT_MODE, secretKey);
              AlgorithmParameters params = cipher.getParameters();
              /** Vecteur d initialisation  */
              byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
              byte[] ciphertext = cipher.doFinal(link.getBytes(StandardCharsets.UTF_8));
              
              // Convert encrypted data to a string for sending in the email
              String encryptedLink = Base64.getEncoder().encodeToString(ciphertext);
              String ivString = Base64.getEncoder().encodeToString(iv);
              String keyString = Base64.getEncoder().encodeToString(secretKey.getEncoded());

              /** end bloc chiffrement */

            inv.setMembre(membre);
            helper.setTo(inv.getEmailInvitee());
            String html = "<html><body style='font-family: Arial, sans-serif; font-size: 16px; color: #333; background-color: #fff; margin: 0;'><p style='font-weight: bold; font-size: 18px;'>Bonjour,</p><p>Vous êtes invité à rejoindre le projet "
                    + p.getNom()
                    + ".</p><p style='color: #777;'>Cliquez sur le lien suivant pour accéder au projet: <a href='"
                    + link + "'>" + encryptedLink + "</a></p><p>Cordialement,<br>Le Chef de projet " + chp.getNom()
                    + "</p></body></html>";
            helper.setText(html, true);
            inv.setMembreId(membre.getId());
        }

        mailSender.send(message);
        inv.setChefProjet(chp);
        inv.setDateExpiration(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)));
        return invitationService.sauvegarderInvitation(request.getInvitation());
    }

    @GetMapping("/{id}")
    public Invitation getInvitationById(@PathVariable("id") Long id) {
        Invitation inv = invitationService.getInvitationById(id);
        ChefProjet chp = chefProjetFeignClient.getChefProjetsById(inv.getChefProjetId());
        Membre membre = membreFeignClient.getMembreById(inv.getMembreId());
        inv.setChefProjet(chp);
        inv.setMembre(membre);
        return inv;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerInvitaion(@PathVariable("id") Long id) {
       boolean deleted =  invitationService.supprimerInvitaion(id);
       if(!deleted)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        else
            return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping
    public List<Invitation> getAllInvitations() {
        return invitationService.getAllInvitations();
    }

}

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
class InvitationProjetRequest {
    private Invitation invitation;
    private Projet projet;  
}
