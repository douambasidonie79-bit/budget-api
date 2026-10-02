package com.sidoniesoft.budget_api.controller;

import com.sidoniesoft.budget_api.entity.Categorie;
import com.sidoniesoft.budget_api.service.CategorieService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategorieController {

    private final CategorieService categorieService;

    public CategorieController(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    // Retourne uniquement les catégories de l'utilisateur connecté
    @GetMapping
    public List<Categorie> getAll(Authentication authentication) {
        String email = authentication.getName();
        return categorieService.findByEmail(email);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categorie> getById(@PathVariable Long id) {
        return ResponseEntity.ok(categorieService.findById(id));
    }

    // Crée une catégorie et l'associe automatiquement à l'utilisateur connecté
    @PostMapping
    public ResponseEntity<Categorie> create(@Valid @RequestBody Categorie categorie,
                                            Authentication authentication) {
        String email = authentication.getName();
        Categorie saved = categorieService.save(categorie, email);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categorieService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categorie> update(@PathVariable Long id,
                                            @Valid @RequestBody Categorie categorie) {
        Categorie updated = categorieService.update(id, categorie);
        return ResponseEntity.ok(updated);
    }
}