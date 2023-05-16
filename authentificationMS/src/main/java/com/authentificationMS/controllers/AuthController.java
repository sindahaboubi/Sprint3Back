package com.authentificationMS.controllers;

import com.authentificationMS.classes.AuthResponse;
import com.authentificationMS.classes.JwtTokenUtil;
import com.authentificationMS.models.ChefProjet;
import com.authentificationMS.models.Credentials;
import com.authentificationMS.models.Membre;
import com.authentificationMS.services.ChefProjetFeignClient;
import com.authentificationMS.services.MembreFeignClient;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;

@RestController
@RequestMapping("/auth")
public class AuthController {
    // Autres annotations et déclarations

    @Autowired
    private ChefProjetFeignClient chefProjetFeignClient;

    @Autowired
    private MembreFeignClient membreFeignClient;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    private boolean checkPassword(String password, String encryptedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(password, encryptedPassword);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody Credentials credentials) {
        String email = credentials.getEmail();
        String password = credentials.getPwd();

        Membre membre = membreFeignClient.getMembreByEmail(email);
        ChefProjet chefProjet = chefProjetFeignClient.getChefProjetByEmail(email);

        if (membre != null && chefProjet != null && checkPassword(password, membre.getPwd()) && checkPassword(password, chefProjet.getPwd())) {
            AuthResponse response = new AuthResponse();
            response.setUserType("chooseType");
            return ResponseEntity.ok(response);
        } else if (chefProjet != null && membre == null) {
            if (checkPassword(password, chefProjet.getPwd())) {
                String token = jwtTokenUtil.generateToken(chefProjet.getEmail());
                AuthResponse response = new AuthResponse();
                response.setToken(token);
                response.setUserType("chefProjet");
                response.setChefProjet(chefProjet);
                return ResponseEntity.ok(response);
            }
        } else if (chefProjet == null && membre != null) {
            if (checkPassword(password, membre.getPwd())) {
                String token = jwtTokenUtil.generateToken(membre.getEmail());
                AuthResponse response = new AuthResponse();
                response.setToken(token);
                response.setUserType("membre");
                response.setMembre(membre);
                return ResponseEntity.ok(response);
            }
        } else if (membre != null && chefProjet != null && !checkPassword(password, membre.getPwd()) || !checkPassword(password, chefProjet.getPwd())){
            AuthResponse response = new AuthResponse();

            if (checkPassword(password, membre.getPwd())) {
                String token = jwtTokenUtil.generateToken(membre.getEmail());
                response.setToken(token);
                response.setUserType("membre");
                response.setMembre(membre);
                return ResponseEntity.ok(response);

            } else if (checkPassword(password, chefProjet.getPwd())) {
                String token = jwtTokenUtil.generateToken(chefProjet.getEmail());
                response.setToken(token);
                response.setUserType("chefProjet");
                response.setChefProjet(chefProjet);
                return ResponseEntity.ok(response);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }




    @PostMapping("/chefProjet")
    public ResponseEntity<AuthResponse> authenticateChefProjet(@RequestBody Credentials credentials) {
        String email = credentials.getEmail();
        String password = credentials.getPwd();

        ChefProjet chefProjet = chefProjetFeignClient.getChefProjetByEmail(email);

        if (chefProjet != null && checkPassword(password, chefProjet.getPwd())) {
            // Générez le token pour le chef de projet et renvoyez-le
            String token = jwtTokenUtil.generateToken(chefProjet.getEmail());
            AuthResponse response = new AuthResponse();
            response.setToken(token);
            response.setUserType("chefProjet");
            return ResponseEntity.ok(response);
        } else {
            // Les informations d'identification sont invalides, renvoie une erreur
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @PostMapping("/membre")
    public ResponseEntity<AuthResponse> authenticateMembre(@RequestBody Credentials credentials) {
        String email = credentials.getEmail();
        String password = credentials.getPwd();

        Membre mmebre = membreFeignClient.getMembreByEmail(email);

        if (mmebre != null && checkPassword(password, mmebre.getPwd())) {
            // Générez le token pour le chef de projet et renvoyez-le
            String token = jwtTokenUtil.generateToken(mmebre.getEmail());
            AuthResponse response = new AuthResponse();
            response.setToken(token);
            response.setUserType("membre");
            return ResponseEntity.ok(response);
        } else {
            // Les informations d'identification sont invalides, renvoie une erreur
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }



    // Méthode pour générer le token en fonction de l'utilisateur (chefProjet ou membre)
    private String generateToken(Membre membre) {
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        // Génération du token JWT en utilisant la clé sécurisée
        String token = Jwts.builder()
                .setSubject(membre.getEmail())
                // Ajoutez d'autres claims ou informations que vous souhaitez inclure dans le token
                .signWith(secretKey)
                .compact();

        return token;
    }

    private String generateToken(ChefProjet chefProjet) {
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        // Génération du token JWT en utilisant la clé sécurisée
        String token = Jwts.builder()
                .setSubject(chefProjet.getEmail())
                // Ajoutez d'autres claims ou informations que vous souhaitez inclure dans le token
                .signWith(secretKey)
                .compact();

        return token;
    }
}

