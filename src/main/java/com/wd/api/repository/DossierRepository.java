package com.wd.api.repository;

import com.wd.api.domain.Dossier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DossierRepository extends JpaRepository<Dossier,Long> {

   List<Dossier> findByUserId(Long id);
}
