package com.wd.api.service.impl;

import com.wd.api.constant.UserImplConstant;
import com.wd.api.domain.Beneficiaire;
import com.wd.api.repository.BeneficiaireRepository;
import com.wd.api.service.BeneficiaireService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@Service @Transactional
public class BeneficiaireImpl implements BeneficiaireService {

    private final BeneficiaireRepository beneficiaireRepository;

    public BeneficiaireImpl(BeneficiaireRepository beneficiaireRepository) {
        this.beneficiaireRepository = beneficiaireRepository;
    }

    @Override
    public Beneficiaire save(Beneficiaire beneficiaire) {
//        beneficiaire.setImageUrl(getTemporaryProfileImageUrl());
        return this.beneficiaireRepository.save(beneficiaire);
    }

    @Override
    public Beneficiaire findOne(Long id) {
        return this.beneficiaireRepository.findById(id).orElse(new Beneficiaire());
    }

    @Override
    public Beneficiaire update(Beneficiaire beneficiaire) {
//        beneficiaire.setImageUrl("getTemporaryProfileImageUrl"());
        return this.beneficiaireRepository.save(beneficiaire);
    }

    @Override
    public List<Beneficiaire> findAll() {
        return this.beneficiaireRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        this.beneficiaireRepository.deleteById(id);
    }

//    private String getTemporaryProfileImageUrl() {
//        return ServletUriComponentsBuilder.fromCurrentContextPath().path(UserImplConstant.DEFAULT_BENEFICIAIRE_IMAGE_PATH).toUriString();
//    }
}
