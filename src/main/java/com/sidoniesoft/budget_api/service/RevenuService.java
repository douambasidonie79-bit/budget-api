package com.sidoniesoft.budget_api.service;

import com.sidoniesoft.budget_api.entity.Categorie;
import com.sidoniesoft.budget_api.entity.Revenu;
import com.sidoniesoft.budget_api.entity.TypeCategorie;
import com.sidoniesoft.budget_api.repository.CategorieRepository;
import com.sidoniesoft.budget_api.repository.RevenuRepository;
import com.sidoniesoft.budget_api.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RevenuService {

    private final RevenuRepository revenuRepository;
    private final CategorieRepository categorieRepository;
    private final UtilisateurRepository utilisateurRepository;

    public RevenuService(
            RevenuRepository revenuRepository,
            CategorieRepository categorieRepository,
            UtilisateurRepository utilisateurRepository
    ) {
        this.revenuRepository = revenuRepository;
        this.categorieRepository = categorieRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    public List<Revenu> findAll() {
        return revenuRepository.findAll();
    }

    public List<Revenu> findByUtilisateurEmail(String email) {
        return revenuRepository.findByUtilisateurEmail(email);
    }

    public List<Revenu> findByUtilisateurId(Long utilisateurId) {
        return revenuRepository.findByUtilisateurId(utilisateurId);
    }

    public Revenu findById(Long id) {
        return revenuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Revenu introuvable avec id: " + id));
    }

    public Revenu save(Revenu revenu) {
        return save(revenu, null);
    }

    public Revenu save(Revenu revenu, String emailConnecte) {
        if (emailConnecte != null) {
            utilisateurRepository.findByEmail(emailConnecte).ifPresent(revenu::setUtilisateur);
        } else if (revenu.getUtilisateur() != null && revenu.getUtilisateur().getId() != null) {
            utilisateurRepository.findById(revenu.getUtilisateur().getId()).ifPresent(revenu::setUtilisateur);
        }

        Categorie categorie = categorieRepository.findById(revenu.getCategorie().getId())
                .orElseThrow(() -> new RuntimeException("Categorie introuvable avec id: " + revenu.getCategorie().getId()));

        if (categorie.getType() != TypeCategorie.REVENU) {
            throw new IllegalArgumentException("La categorie choisie n'est pas de type REVENU");
        }

        revenu.setCategorie(categorie);
        return revenuRepository.save(revenu);
    }

    public void deleteById(Long id) {
        revenuRepository.deleteById(id);
    }
        public Revenu update(Long id, Revenu revenuModifie) {
        Revenu revenuExistant = revenuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Revenu introuvable avec id: " + id));

        Categorie categorie = categorieRepository.findById(revenuModifie.getCategorie().getId())
                .orElseThrow(() -> new RuntimeException("Categorie introuvable"));

        if (categorie.getType() != TypeCategorie.REVENU) {
            throw new IllegalArgumentException("La categorie choisie n'est pas de type REVENU");
        }

        revenuExistant.setMontant(revenuModifie.getMontant());
        revenuExistant.setDateRevenu(revenuModifie.getDateRevenu());
        revenuExistant.setDescription(revenuModifie.getDescription());
        revenuExistant.setCategorie(categorie);

        return revenuRepository.save(revenuExistant);
    }
}