package com.wd.api.service;

import com.wd.api.domain.Beneficiaire;
import com.wd.api.domain.Depence;
import com.wd.api.repository.DepenceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @Transactional
public class DepenceServiceImpl implements DepenceService{

    private  final DepenceRepository depenceRepository;


    public DepenceServiceImpl(DepenceRepository depenceRepository) {
        this.depenceRepository = depenceRepository;

    }

    /**
     * @param depence
     * @return
     */
    @Override
    public Depence create(Depence depence) {
        return this.depenceRepository.save(depence);
    }

    /**
     * @param depence
     * @return
     */
    @Override
    public Depence update(Depence depence) { return this.depenceRepository.save(depence);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Depence find(Long id) {
        return this.depenceRepository.findById(id).get();
    }

    /**
     * @param id
     * @return
     */


    /**
     * @param id
     * @return
     */
    @Override
    public List<Depence> findByBeneficiareId(Long id) {
        return this.depenceRepository.findByBeneficiareId(id);
    }

    /**
     * @param id
     */
    @Override
    public void delete(Long id) {
     this.depenceRepository.deleteById(id);
    }

    /**
     * @return
     */
    @Override
    public List<Depence> list() {
        return this.depenceRepository.findAll();
    }
}
