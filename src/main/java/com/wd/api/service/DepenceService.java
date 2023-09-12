package com.wd.api.service;

import com.wd.api.domain.Depence;

import java.util.List;

public interface DepenceService {

    Depence create(Depence depence);
    Depence update(Depence depence);
    Depence find(Long id);
    List<Depence> findByBeneficiareId(Long id);
    void delete(Long id);
    List<Depence> list();

}
