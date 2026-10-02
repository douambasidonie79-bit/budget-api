package com.sidoniesoft.budget_api.service;

import com.sidoniesoft.budget_api.entity.Categorie;
import com.sidoniesoft.budget_api.entity.Depense;
import com.sidoniesoft.budget_api.entity.TypeCategorie;
import com.sidoniesoft.budget_api.repository.CategorieRepository;
import com.sidoniesoft.budget_api.repository.DepenseRepository;
import com.sidoniesoft.budget_api.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepenseService {

    private final DepenseRepository depenseRepository;
    private final CategorieRepository categorieRepository;
    private final UtilisateurRepository utilisateurRepository;

    public DepenseService(
            DepenseRepository depenseRepository,
            CategorieRepository categorieRepository,
            UtilisateurRepository utilisateurRepository
    ) {
        this.depenseRepository = depenseRepository;
        this.categorieRepository = categorieRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    public List<Depense> findAll() {
        return depenseRepository.findAll();
    }

    public List<Depense> findByUtilisateurEmail(String email) {
        return depenseRepository.findByUtilisateurEmail(email);
    }

    public List<Depense> findByUtilisateurId(Long utilisateurId) {
        return depenseRepository.findByUtilisateurId(utilisateurId);
    }

    public Depense findById(Long id) {
        return depenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Depense introuvable avec id: " + id));
    }

    public Depense save(Depense depense) {
        return save(depense, null);
    }

    public Depense save(Depense depense, String emailConnecte) {
        if (emailConnecte != null) {
            utilisateurRepository.findByEmail(emailConnecte).ifPresent(depense::setUtilisateur);
        } else if (depense.getUtilisateur() != null && depense.getUtilisateur().getId() != null) {
            utilisateurRepository.findById(depense.getUtilisateur().getId()).ifPresent(depense::setUtilisateur);
        }

        Categorie categorie = categorieRepository.findById(depense.getCategorie().getId())
                .orElseThrow(() -> new RuntimeException("Categorie introuvable avec id: " + depense.getCategorie().getId()));

        if (categorie.getType() != TypeCategorie.DEPENSE) {
            throw new IllegalArgumentException("La categorie choisie n'est pas de type DEPENSE");
        }

        depense.setCategorie(categorie);
        return depenseRepository.save(depense);
    }

    public void deleteById(Long id) {
        depenseRepository.deleteById(id);
    }
        public Depense update(Long id, Depense depenseModifiee) {
        Depense depenseExistante = depenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Depense introuvable avec id: " + id));

        Categorie categorie = categorieRepository.findById(depenseModifiee.getCategorie().getId())
                .orElseThrow(() -> new RuntimeException("Categorie introuvable"));

        if (categorie.getType() != TypeCategorie.DEPENSE) {
            throw new IllegalArgumentException("La categorie choisie n'est pas de type DEPENSE");
        }

        depenseExistante.setMontant(depenseModifiee.getMontant());
        depenseExistante.setDateDepense(depenseModifiee.getDateDepense());
        depenseExistante.setDescription(depenseModifiee.getDescription());
        depenseExistante.setCategorie(categorie);

        return depenseRepository.save(depenseExistante);
    }
}