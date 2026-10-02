package com.sidoniesoft.budget_api.controller;

import com.sidoniesoft.budget_api.entity.Revenu;
import com.sidoniesoft.budget_api.service.RevenuService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/revenus")
public class RevenuController {

    private final RevenuService revenuService;

    public RevenuController(RevenuService revenuService) {
        this.revenuService = revenuService;
    }

    @GetMapping
    public List<Revenu> getAll(
            Authentication authentication,
            @RequestParam(required = false) Long utilisateurId
    ) {
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getName())) {
            return revenuService.findByUtilisateurEmail(authentication.getName());
        }
        if (utilisateurId != null) {
            return revenuService.findByUtilisateurId(utilisateurId);
        }
        return revenuService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Revenu> getById(@PathVariable Long id) {
        return ResponseEntity.ok(revenuService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Revenu> create(
            @Valid @RequestBody Revenu revenu,
            Authentication authentication
    ) {
        String email = (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getName()))
                ? authentication.getName() : null;
        Revenu saved = revenuService.save(revenu, email);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        revenuService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Revenu> update(@PathVariable Long id, @Valid @RequestBody Revenu revenu) {
        Revenu updated = revenuService.update(id, revenu);
        return ResponseEntity.ok(updated);
    }
}