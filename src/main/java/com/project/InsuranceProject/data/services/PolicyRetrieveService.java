package com.project.InsuranceProject.data.services;

import com.project.InsuranceProject.data.entity.Claim;
import com.project.InsuranceProject.data.entity.Policy;
import com.project.InsuranceProject.data.repositories.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Policy savePolicy(Policy policy) {
        return policyRepository.save(policy);
    }

    @Transactional(readOnly = true)
    public List<Policy> getPoliciesByAgentId(Long agentId) {
        return policyRepository.findByAgentId(agentId);
    }
    @Transactional
    public void approvePolicy(Policy policy) {
        policy.setUrStatus("APPROVED");
        policyRepository.save(policy);
    }
    @Transactional
    public void denyPolicy(Policy policy) {
        policy.setUrStatus("DENIED");
        policyRepository.save(policy);
    }
    @Transactional
    public void confirmPolicy(Policy policy) {
        policy.setStatus("UR");
        policyRepository.save(policy);
    }

    public List<Claim> getClaimsForPolicy(Policy policy) {
        return null;
    }
}
