package com.wd.api.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface DashboardService {

    public List<Object[]> getCountByTypeDepence() ;
    public Map<String,Long> getMapCount();
}
