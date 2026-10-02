package com.sidoniesoft.budget_api.service;

import com.sidoniesoft.budget_api.entity.Utilisateur;
import com.sidoniesoft.budget_api.repository.UtilisateurRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    public UtilisateurService(UtilisateurRepository utilisateurRepository, PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Utilisateur> findAll() {
        return utilisateurRepository.findAll();
    }

    public Utilisateur findByEmail(String email) {
        return utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable avec email: " + email));
    }

    public Utilisateur findById(Long id) {
        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable avec id: " + id));
    }

    public Utilisateur save(Utilisateur utilisateur) {
        if (utilisateur.getDateCreation() == null) {
            utilisateur.setDateCreation(LocalDateTime.now());
        }
        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
        return utilisateurRepository.save(utilisateur);
    }

    public Utilisateur update(Long id, Utilisateur utilisateurModifie) {
        Utilisateur utilisateurExistant = findById(id);

        utilisateurExistant.setNom(utilisateurModifie.getNom());
        utilisateurExistant.setEmail(utilisateurModifie.getEmail());

        if (utilisateurModifie.getMotDePasse() != null && !utilisateurModifie.getMotDePasse().isBlank()) {
            utilisateurExistant.setMotDePasse(passwordEncoder.encode(utilisateurModifie.getMotDePasse()));
        }

        return utilisateurRepository.save(utilisateurExistant);
    }

    public void deleteById(Long id) {
        utilisateurRepository.deleteById(id);
    }
}