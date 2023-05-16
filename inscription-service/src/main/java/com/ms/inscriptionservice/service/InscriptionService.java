package com.ms.inscriptionservice.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ms.inscriptionservice.model.ChefProjet;
import com.ms.inscriptionservice.model.Membre;

@Service
public class InscriptionService {

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private ChefProjetFeignClient chefProjetFeignClient;

    @Autowired
    private MembreFeignClient membreFeignClient;
    
    public ChefProjet inscrirChefProjet(ChefProjet chef){

        if(chefProjetFeignClient.getChefProjetByEmail(chef.getEmail())!=null)
            return null ;
        else{
            /** crypt  */
            String hashedPassword = passwordEncoder.encode(chef.getPwd());
            chef.setPwd(hashedPassword);
            /** end */
            chef.setDateInscription(new Date());
            return this.chefProjetFeignClient.ajouterChefProjet(chef);
        }
        
    }


    public Membre inscrirMembre(Membre membre){

        if(membreFeignClient.getMembreByEmail(membre.getEmail())!=null)
            return null  ;
        else{
            /** crypt  */
            
            String hashedPassword = passwordEncoder.encode(membre.getPwd());
            membre.setPwd(hashedPassword);
            /** end */
            membre.setDateInscription(new Date());
            return this.membreFeignClient.ajouterMembre(membre);
        }
    }

    public Membre modifierInscriptionMembre(Membre membre){
         /** crypt  */
        String hashedPassword = passwordEncoder.encode(membre.getPwd());
        membre.setPwd(hashedPassword);
        /** end */
        membre.setDateInscription(new Date());
        return this.membreFeignClient.modifierMembre(membre);
    }

    /** bech njareb l conversion des bytes correcte wala le w zid test 3al form li tji */
    public void saveImage(byte[] bytes, String fileName, String filePath) throws IOException {
        File file = new File(filePath + File.separator + fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(bytes);
        fos.flush();
        fos.close();
    }


}
