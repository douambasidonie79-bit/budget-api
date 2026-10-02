package com.sidoniesoft.budget_api.service;

import com.sidoniesoft.budget_api.entity.Categorie;
import com.sidoniesoft.budget_api.entity.Utilisateur;
import com.sidoniesoft.budget_api.repository.CategorieRepository;
import com.sidoniesoft.budget_api.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorieService {

    private final CategorieRepository categorieRepository;
    private final UtilisateurRepository utilisateurRepository;

    public CategorieService(CategorieRepository categorieRepository,
                            UtilisateurRepository utilisateurRepository) {
        this.categorieRepository = categorieRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    public List<Categorie> findAll() {
        return categorieRepository.findAll();
    }

    // Retourne uniquement les catégories de l'utilisateur connecté
    public List<Categorie> findByEmail(String email) {
        return categorieRepository.findByUtilisateurEmail(email);
    }

    public Categorie findById(Long id) {
        return categorieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categorie introuvable avec id: " + id));
    }

    // Crée une catégorie en l'associant à l'utilisateur connecté
    public Categorie save(Categorie categorie, String email) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable: " + email));
        categorie.setUtilisateur(utilisateur);
        return categorieRepository.save(categorie);
    }

    public void deleteById(Long id) {
        categorieRepository.deleteById(id);
    }

    public Categorie update(Long id, Categorie categorieModifiee) {
        Categorie categorieExistante = categorieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categorie introuvable avec id: " + id));

        categorieExistante.setNom(categorieModifiee.getNom());
        categorieExistante.setType(categorieModifiee.getType());

        return categorieRepository.save(categorieExistante);
    }
}