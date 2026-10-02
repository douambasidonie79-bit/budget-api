package com.sidoniesoft.budget_api.controller;

import com.sidoniesoft.budget_api.entity.Utilisateur;
import com.sidoniesoft.budget_api.service.UtilisateurService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping
    public List<Utilisateur> getAll(org.springframework.security.core.Authentication authentication) {
        String email = authentication.getName();
        Utilisateur user = utilisateurService.findByEmail(email);
        return List.of(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getById(@PathVariable Long id) {
        return ResponseEntity.ok(utilisateurService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Utilisateur> create(@Valid @RequestBody Utilisateur utilisateur) {
        Utilisateur saved = utilisateurService.save(utilisateur);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        utilisateurService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
        @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> update(@PathVariable Long id, @Valid @RequestBody Utilisateur utilisateur) {
        Utilisateur updated = utilisateurService.update(id, utilisateur);
        return ResponseEntity.ok(updated);
    }
}