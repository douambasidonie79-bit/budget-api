package com.sidoniesoft.budget_api.dto;

import com.sidoniesoft.budget_api.entity.TypeCategorie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategorieDTO {
    private Long id;
    private String nom;
    private TypeCategorie type;
    private String description;
}
