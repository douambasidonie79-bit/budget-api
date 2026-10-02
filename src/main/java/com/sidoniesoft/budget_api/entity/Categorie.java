package com.sidoniesoft.budget_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categorie")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom de la categorie est obligatoire")
    @Column(name = "nom", nullable = false, length = 100)
    private String nom;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TypeCategorie type;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    @JsonIgnoreProperties("motDePasse")
    private Utilisateur utilisateur;
}