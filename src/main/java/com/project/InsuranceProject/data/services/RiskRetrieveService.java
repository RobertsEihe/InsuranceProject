package com.project.InsuranceProject.data.services;

import com.project.InsuranceProject.data.entity.Risk;
import com.project.InsuranceProject.data.repositories.RiskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RiskRetrieveService {
    @Autowired
    RiskRepository riskRepository;

    public Optional<Risk> findByNameAndType(String name, String type) {
        return riskRepository.findByRiskAndType(name, type);
    }
}
