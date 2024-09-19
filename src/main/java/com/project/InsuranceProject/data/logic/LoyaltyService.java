package com.project.InsuranceProject.data.logic;

import com.project.InsuranceProject.data.entity.Policy;
import com.project.InsuranceProject.data.entity.Users;
import com.project.InsuranceProject.data.repositories.PolicyRepository;
import com.project.InsuranceProject.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LoyaltyService {

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private UserRepository userRepository;

    public int calculateLoyalty(Users customer) {
        if (customer.getLoyalty() < 1 || customer.getLoyalty() > 5) {
            customer.setLoyalty(1);
            userRepository.save(customer);
        }

        if (customer.getLoyalty() >= 5) {
            return 5;
        }

        List<Policy> policies = policyRepository.findByCustomerIdOrderByStartDateAsc(customer.getId());

        if (policies.isEmpty()) {
            return customer.getLoyalty();
        }

        LocalDate firstPolicyStartDate = policies.get(0).getStart_date();

        int humanMonths = calculateMonthsBetween(firstPolicyStartDate, LocalDate.now());
        int coveredMonths = calculateTotalCoveredMonths(policies);
        int newLoyaltyPoints = calculateLoyaltyPoints(humanMonths, coveredMonths);
        int newLoyaltyScore = Math.min(customer.getLoyalty() + newLoyaltyPoints, 5);

        if (newLoyaltyScore != customer.getLoyalty()) {
            customer.setLoyalty(newLoyaltyScore);
            userRepository.save(customer);
        }

        return newLoyaltyScore;
    }

    private int calculateTotalCoveredMonths(List<Policy> policies) {
        int totalMonths = 0;

        for (Policy policy : policies) {
            totalMonths += calculateMonthsBetween(policy.getStart_date(), policy.getEnd_date());
        }

        return totalMonths;
    }

    private int calculateLoyaltyPoints(int humanMonths, int coveredMonths) {
        int loyaltyPoints = 0;

        int fullYearPeriods = humanMonths / 12;

        for (int i = 1; i <= fullYearPeriods; i++) {
            int requiredCoverage = i * 12;

            if (coveredMonths >= requiredCoverage / 2) {
                loyaltyPoints++;
            }
        }

        return loyaltyPoints;
    }

    private int calculateMonthsBetween(LocalDate startDate, LocalDate endDate) {
        return (int) ChronoUnit.MONTHS.between(startDate, endDate.plusDays(1));
    }
}