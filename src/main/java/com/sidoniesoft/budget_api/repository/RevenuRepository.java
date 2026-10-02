package com.sidoniesoft.budget_api.repository;
import com.sidoniesoft.budget_api.entity.Revenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RevenuRepository extends JpaRepository<Revenu, Long> {
    List<Revenu> findByUtilisateurId(Long utilisateurId);
    List<Revenu> findByUtilisateurEmail(String email);
}