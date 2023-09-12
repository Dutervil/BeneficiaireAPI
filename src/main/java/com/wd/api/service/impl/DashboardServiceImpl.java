package com.wd.api.service.impl;

import com.wd.api.repository.BeneficiaireRepository;
import com.wd.api.repository.DepenceRepository;
import com.wd.api.repository.UserRepository;
import com.wd.api.service.DashboardService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final DepenceRepository depenceRepository;
    private final UserRepository userRepository;
    private final BeneficiaireRepository beneficiaireRepository;

    public DashboardServiceImpl(DepenceRepository depenceRepository, UserRepository userRepository, BeneficiaireRepository beneficiaireRepository) {
        this.depenceRepository = depenceRepository;
        this.userRepository = userRepository;
        this.beneficiaireRepository = beneficiaireRepository;
    }

    /**
     * @return
     */
    @Override
    public List<Object[]> getCountByTypeDepence() {
        return depenceRepository.getCountByTypeDepence();
    }

    /**
     * @return
     */
    @Override
    public Map<String, Long> getMapCount() {
        Map<String,Long> data=new HashMap<>();
        data.put("user",this.userRepository.getTotalCount());
        data.put("depence",this.depenceRepository.getTotalCount());
        data.put("beneficiaire",this.beneficiaireRepository.getTotalCount());
        return data;
    }
}
