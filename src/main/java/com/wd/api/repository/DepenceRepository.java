package com.wd.api.repository;

import com.wd.api.domain.Depence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepenceRepository extends JpaRepository<Depence,Long> {

    List<Depence> findByBeneficiareId(Long beneficiareId);
}
