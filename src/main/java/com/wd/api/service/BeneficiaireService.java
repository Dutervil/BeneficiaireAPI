package com.wd.api.service;

import com.wd.api.domain.Beneficiaire;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface BeneficiaireService {

    Beneficiaire save(Beneficiaire beneficiaire);
    Beneficiaire findOne(Long id);
    Beneficiaire update(Beneficiaire beneficiaire);
    List<Beneficiaire> findAll();
    void delete(Long id);
}
