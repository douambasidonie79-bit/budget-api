package com.sidoniesoft.budget_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RevenuDTO {
    private Long id;
    private BigDecimal montant;
    private LocalDate dateRevenu;
    private String description;
    private CategorieDTO categorie;
    private Long utilisateurId;
}
