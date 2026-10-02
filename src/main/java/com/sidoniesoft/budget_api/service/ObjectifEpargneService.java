package com.sidoniesoft.budget_api.service;

import com.sidoniesoft.budget_api.entity.ObjectifEpargne;
import com.sidoniesoft.budget_api.repository.ObjectifEpargneRepository;
import com.sidoniesoft.budget_api.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ObjectifEpargneService {

    private final ObjectifEpargneRepository objectifEpargneRepository;
    private final UtilisateurRepository utilisateurRepository;

    public ObjectifEpargneService(
            ObjectifEpargneRepository objectifEpargneRepository,
            UtilisateurRepository utilisateurRepository
    ) {
        this.objectifEpargneRepository = objectifEpargneRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    public List<ObjectifEpargne> findAll() {
        return objectifEpargneRepository.findAll();
    }

    public List<ObjectifEpargne> findByUtilisateurEmail(String email) {
        return objectifEpargneRepository.findByUtilisateurEmail(email);
    }

    public List<ObjectifEpargne> findByUtilisateurId(Long utilisateurId) {
        return objectifEpargneRepository.findByUtilisateurId(utilisateurId);
    }

    public ObjectifEpargne findById(Long id) {
        return objectifEpargneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Objectif introuvable avec id: " + id));
    }

    public ObjectifEpargne save(ObjectifEpargne objectif) {
        return save(objectif, null);
    }

    public ObjectifEpargne save(ObjectifEpargne objectif, String emailConnecte) {
        if (emailConnecte != null) {
            utilisateurRepository.findByEmail(emailConnecte).ifPresent(objectif::setUtilisateur);
        } else if (objectif.getUtilisateur() != null && objectif.getUtilisateur().getId() != null) {
            utilisateurRepository.findById(objectif.getUtilisateur().getId()).ifPresent(objectif::setUtilisateur);
        }

        if (objectif.getMontantActuel() == null) {
            objectif.setMontantActuel(BigDecimal.ZERO);
        }
        return objectifEpargneRepository.save(objectif);
    }

    public ObjectifEpargne update(Long id, ObjectifEpargne objectifModifie) {
        ObjectifEpargne objectifExistant = findById(id);

        objectifExistant.setNom(objectifModifie.getNom());
        objectifExistant.setMontantCible(objectifModifie.getMontantCible());
        objectifExistant.setMontantActuel(objectifModifie.getMontantActuel());
        objectifExistant.setDateLimite(objectifModifie.getDateLimite());

        return objectifEpargneRepository.save(objectifExistant);
    }

    public void deleteById(Long id) {
        objectifEpargneRepository.deleteById(id);
    }
}