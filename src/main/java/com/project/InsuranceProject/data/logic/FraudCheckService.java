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
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FraudCheckService {

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private ClaimRepository claimRepository;

    //@Autowired
    //private IncidentScoreService incidentScoreService;


//     This method checks whether a user is flagged as fraudulent based on their claim history.
//     @param customerId The ID of the customer whose claim history is being analyzed.
//      @return boolean (True = fraud suspected, False = no fraud detected).
//
    public boolean fraudCheck(Long customerId) {



        // Retrieve all policies for the customer
        //List<Policy> policies = policyRepository.findByCustomerId(customerId);
        List<Policy> policies = policyRepository.findByUsers_Id(customerId);

        // Retrieve all claims for the customer’s policies
//        List<Claim> claims = claimsRepository.findByPolicyIdIn(
//                policies.stream().map(Policy::getId).collect(Collectors.toList())
//        );


        List<Claim> claims = claimRepository.findByUserId(customerId);

        // If no claims, no fraud is detected
        if (claims.isEmpty()) {
            return false;
        }

        // Check recent claim cases within the last 3 months
        LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);
        long recentClaimsCount = claims.stream()
                .filter(claim -> claim.getDate().isAfter(threeMonthsAgo))
                .count();

        // Flag as fraud if there are recent claims within the last 3 months
        if (recentClaimsCount > 0) {
            return true;
        }

        // Check if there are 3 or more claims of the same insurance type across the customer's history
        Map<String, Long> claimTypeCount = claims.stream()
                .collect(Collectors.groupingBy(Claim::getType, Collectors.counting()));

        boolean hasExcessiveSameTypeClaims = claimTypeCount.values().stream()
                .anyMatch(count -> count >= 3);

        // Flag as fraud if there are 3 or more claims of the same type
        if (hasExcessiveSameTypeClaims) {
            return true;
        }

        // Check the timing of claims relative to policy start date
        for (Claim claim : claims) {
            Policy policy = claim.getPolicy();
            long monthsBetween = ChronoUnit.MONTHS.between(policy.getStart_date(), claim.getDate());

            // Flag as fraud if the claim is made too soon after the policy was issued (e.g., within 1 month)
            if (monthsBetween <= 1) {
                return true;
            }
        }

        // Analyze the customer's incident score
        //int incidentScore = incidentScoreService.calculateIncidentScore(customerId);

        // Flag as fraud if the incident score is high (e.g., >= 4)
//        if (incidentScore >= 4) {
//            return true;
//        }

        // If none of the fraud conditions are met, return false
        return false;



    }
}