package com.sidoniesoft.budget_api.security;

public class LoginResponse {

    private String token;
    private Long utilisateurId;
    private String nom;
    private String email;

    public LoginResponse(String token, Long utilisateurId, String nom, String email) {
        this.token = token;
        this.utilisateurId = utilisateurId;
        this.nom = nom;
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public Long getUtilisateurId() {
        return utilisateurId;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }
}