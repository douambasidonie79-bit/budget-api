package com.sidoniesoft.budget_api.repository;
import com.sidoniesoft.budget_api.entity.Depense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



import java.util.List;

@Repository
public interface DepenseRepository extends JpaRepository<Depense, Long> {
    List<Depense> findByUtilisateurId(Long utilisateurId);
    List<Depense> findByUtilisateurEmail(String email);
}