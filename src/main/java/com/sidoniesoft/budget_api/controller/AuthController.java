package com.sidoniesoft.budget_api.controller;

import com.sidoniesoft.budget_api.entity.Utilisateur;
import com.sidoniesoft.budget_api.repository.UtilisateurRepository;
import com.sidoniesoft.budget_api.security.JwtUtil;
import com.sidoniesoft.budget_api.security.LoginRequest;
import com.sidoniesoft.budget_api.security.LoginResponse;
import com.sidoniesoft.budget_api.service.UtilisateurService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UtilisateurRepository utilisateurRepository;
    private final UtilisateurService utilisateurService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UtilisateurRepository utilisateurRepository,
                          UtilisateurService utilisateurService,
                          PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil) {
        this.utilisateurRepository = utilisateurRepository;
        this.utilisateurService = utilisateurService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(loginRequest.getEmail())
                .orElse(null);

        if (utilisateur == null || !passwordEncoder.matches(loginRequest.getMotDePasse(), utilisateur.getMotDePasse())) {
            return ResponseEntity.status(401).body("Email ou mot de passe incorrect");
        }

        String token = jwtUtil.generateToken(utilisateur.getEmail());

        LoginResponse response = new LoginResponse(
                token,
                utilisateur.getId(),
                utilisateur.getNom(),
                utilisateur.getEmail()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/inscription")
    public ResponseEntity<?> inscription(@Valid @RequestBody Utilisateur utilisateur) {
        // Vérifier si l'email est déjà utilisé
        if (utilisateurRepository.findByEmail(utilisateur.getEmail()).isPresent()) {
            return ResponseEntity.status(409).body("Cet email est déjà utilisé");
        }

        // Sauvegarder le nouvel utilisateur (le mot de passe sera hashé dans le service)
        Utilisateur saved = utilisateurService.save(utilisateur);

        // Générer un token et retourner la réponse de connexion directement
        String token = jwtUtil.generateToken(saved.getEmail());

        LoginResponse response = new LoginResponse(
                token,
                saved.getId(),
                saved.getNom(),
                saved.getEmail()
        );

        return ResponseEntity.ok(response);
    }
}