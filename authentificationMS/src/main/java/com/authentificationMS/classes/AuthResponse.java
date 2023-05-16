package com.authentificationMS.classes;

import com.authentificationMS.models.ChefProjet;
import com.authentificationMS.models.Membre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthResponse {
    private String token;
    private String userType; // chefProjet ou membre
    private Membre membre;
    private ChefProjet chefProjet;
}

