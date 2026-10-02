package com.sidoniesoft.budget_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "objectif_epargne")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ObjectifEpargne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom de l'objectif est obligatoire")
    @Column(name = "nom", nullable = false, length = 100)
    private String nom;

    @NotNull(message = "Le montant cible est obligatoire")
    @Positive(message = "Le montant cible doit etre positif")
    @Column(name = "montant_cible", nullable = false, precision = 10, scale = 2)
    private BigDecimal montantCible;

    @Column(name = "montant_actuel", precision = 10, scale = 2)
    private BigDecimal montantActuel = BigDecimal.ZERO;

    @Column(name = "date_limite")
    private LocalDate dateLimite;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;
}