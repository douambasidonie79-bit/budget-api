package com.sidoniesoft.budget_api.controller;

import com.sidoniesoft.budget_api.entity.Depense;
import com.sidoniesoft.budget_api.service.DepenseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/depenses")
public class DepenseController {

    private final DepenseService depenseService;

    public DepenseController(DepenseService depenseService) {
        this.depenseService = depenseService;
    }

    @GetMapping
    public List<Depense> getAll(
            Authentication authentication,
            @RequestParam(required = false) Long utilisateurId
    ) {
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getName())) {
            return depenseService.findByUtilisateurEmail(authentication.getName());
        }
        if (utilisateurId != null) {
            return depenseService.findByUtilisateurId(utilisateurId);
        }
        return depenseService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Depense> getById(@PathVariable Long id) {
        return ResponseEntity.ok(depenseService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Depense> create(
            @Valid @RequestBody Depense depense,
            Authentication authentication
    ) {
        String email = (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getName()))
                ? authentication.getName() : null;
        Depense saved = depenseService.save(depense, email);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        depenseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Depense> update(@PathVariable Long id, @Valid @RequestBody Depense depense) {
        Depense updated = depenseService.update(id, depense);
        return ResponseEntity.ok(updated);
    }
}