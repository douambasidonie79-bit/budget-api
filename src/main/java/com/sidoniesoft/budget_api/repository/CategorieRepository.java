package com.sidoniesoft.budget_api.repository;

import com.sidoniesoft.budget_api.entity.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    List<Categorie> findByUtilisateurEmail(String email);
}