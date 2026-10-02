package com.sidoniesoft.budget_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObjectifEpargneDTO {
    private Long id;
    private String nom;
    private BigDecimal montantCible;
    private BigDecimal montantActuel;
    private LocalDate dateLimite;
    private Long utilisateurId;
}
