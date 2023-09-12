package com.wd.api.repository;

import com.wd.api.domain.Depence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepenceRepository extends JpaRepository<Depence,Long> {

    List<Depence> findByBeneficiareId(Long beneficiareId);
    @Query("SELECT sum (d.montant) AS qty, d.typeDepence AS typeDepence FROM Depence d GROUP BY d.typeDepence")
    List<Object[]> getCountByTypeDepence();

    @Query("SELECT SUM(d.montant) FROM Depence d")
    Long getTotalCount();
}
