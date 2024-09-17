package com.project.InsuranceProject.data.logic;

import com.project.InsuranceProject.data.entity.Claim;
import com.project.InsuranceProject.data.entity.Policy;
import com.project.InsuranceProject.data.repositories.ClaimRepository;
import com.project.InsuranceProject.data.repositories.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IncidentScoreService {

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private ClaimRepository claimRepository;

    public int calculateIncidentScore(Long customerId) {

        List<Policy> policies = policyRepository.findByUsers_Id(customerId);
        List<Claim> claims = claimRepository.findByUserId(customerId);
        int policyAmount = policies.size();
        int claimAmount = claims.size();


        if(claimAmount == 0){
            return 0;
        } else {
            return claimAmount * 100 / policyAmount / 10;
        }


    }
}
