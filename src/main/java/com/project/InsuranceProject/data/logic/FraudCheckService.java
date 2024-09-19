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

    public boolean fraudCheck(Long customerId) {

        List<Policy> policies = policyRepository.findByUsers_Id(customerId);
        List<Claim> claims = claimRepository.findByUserId(customerId);

        if (claims.isEmpty()) {
            return false;
        }

        LocalDate oneMonthsAgo = LocalDate.now().minusMonths(1);
        long recentClaimsCount = claims.stream()
                .filter(claim -> claim.getDate().isAfter(oneMonthsAgo))
                .count();

        if (recentClaimsCount > 1) {
            return true;
        }

        // 3 or more claims of the same insurance type across the customers history
        Map<String, Long> claimTypeCount = claims.stream()
                .collect(Collectors.groupingBy(Claim::getType, Collectors.counting()));

        boolean hasExcessiveSameTypeClaims = claimTypeCount.values().stream()
                .anyMatch(count -> count >= 3);

        if (hasExcessiveSameTypeClaims) {
            return true;
        }

        // Check the timing of claims relative to policy start date (1 month)
        for (Claim claim : claims) {
            Policy policy = claim.getPolicy();
            long monthsBetween = ChronoUnit.MONTHS.between(policy.getStart_date(), claim.getDate());

            if (monthsBetween <= 1) {
                return true;
            }
        }

        return false;

    }
}