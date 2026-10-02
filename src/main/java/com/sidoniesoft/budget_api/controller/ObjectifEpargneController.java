package com.sidoniesoft.budget_api.controller;

import com.sidoniesoft.budget_api.entity.ObjectifEpargne;
import com.sidoniesoft.budget_api.service.ObjectifEpargneService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/objectifs")
public class ObjectifEpargneController {

    private final ObjectifEpargneService objectifEpargneService;

    public ObjectifEpargneController(ObjectifEpargneService objectifEpargneService) {
        this.objectifEpargneService = objectifEpargneService;
    }

    @GetMapping
    public List<ObjectifEpargne> getAll(
            Authentication authentication,
            @RequestParam(required = false) Long utilisateurId
    ) {
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getName())) {
            return objectifEpargneService.findByUtilisateurEmail(authentication.getName());
        }
        if (utilisateurId != null) {
            return objectifEpargneService.findByUtilisateurId(utilisateurId);
        }
        return objectifEpargneService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObjectifEpargne> getById(@PathVariable Long id) {
        return ResponseEntity.ok(objectifEpargneService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ObjectifEpargne> create(
            @Valid @RequestBody ObjectifEpargne objectif,
            Authentication authentication
    ) {
        String email = (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getName()))
                ? authentication.getName() : null;
        ObjectifEpargne saved = objectifEpargneService.save(objectif, email);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ObjectifEpargne> update(@PathVariable Long id, @Valid @RequestBody ObjectifEpargne objectif) {
        ObjectifEpargne updated = objectifEpargneService.update(id, objectif);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        objectifEpargneService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}