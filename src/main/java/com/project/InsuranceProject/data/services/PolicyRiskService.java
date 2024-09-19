package com.project.InsuranceProject.data.services;

import com.project.InsuranceProject.data.entity.Policy_risk;
import com.project.InsuranceProject.data.repositories.PolicyRiskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolicyRiskService {

    @Autowired
    private PolicyRiskRepository policyRiskRepository;

    public Policy_risk savePolicyRisk(Policy_risk policyRisk) {
        return policyRiskRepository.save(policyRisk);
    }

    public List<Policy_risk> findByPolicyID(Long id) {
        return policyRiskRepository.findByPolicyID(id);
    }

    public List<Policy_risk> getPolicyRiskByUsername(String username) {return policyRiskRepository.getPolicyRiskByUsername(username);}
}
