package com.wd.api.repository;

import com.wd.api.domain.Beneficiaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BeneficiaireRepository extends JpaRepository<Beneficiaire,Long> {

    @Query("SELECT COUNT(b) FROM Beneficiaire b")
    Long getTotalCount();

    @Query("SELECT COUNT(b) as qty, b.status FROM Beneficiaire b group by b.status ")
    List<Map<Integer,String>> getAmountByStatus();
}
