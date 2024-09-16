package com.project.InsuranceProject.data.services;

import com.project.InsuranceProject.data.entity.Policy;
import com.project.InsuranceProject.data.repositories.PolicyRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolicyRetrieveService {

    @Autowired
    private PolicyRepository policyRepository;

    public List<Policy>getPolicyCustomer(long policy) {
        return policyRepository.findByUsers_Id(policy);
    }
    public List<Policy> getPolicyByUsername(String username) {
        return policyRepository.findByUsers_Username(username);
    }

}
