package com.project.InsuranceProject.data.logic;

import com.project.InsuranceProject.data.entity.Policy;
import com.project.InsuranceProject.data.entity.Policy_risk;
import com.project.InsuranceProject.data.entity.Risk;
import com.project.InsuranceProject.data.entity.Users;
import com.project.InsuranceProject.data.repositories.RiskRepository;
import com.project.InsuranceProject.data.services.PolicyRiskService;
import com.project.InsuranceProject.data.services.PolicyService;
import com.project.InsuranceProject.data.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolicyLogic {

    private final UserService userService;
    private final PolicyService policyService;
    private final PolicyRiskService policyRiskService;
    private final RiskRepository riskRepository;
    private final LoyaltyService loyaltyService;
    private final IncidentScoreService incidentScoreService;
    private final FraudCheckService fraudCheckService;

    public PolicyLogic(UserService userService, PolicyService policyService, PolicyRiskService policyRiskService, RiskRepository riskRepository, LoyaltyService loyaltyService, IncidentScoreService incidentScoreService, FraudCheckService fraudCheckService) {
        this.userService = userService;
        this.policyService = policyService;
        this.policyRiskService = policyRiskService;
        this.riskRepository = riskRepository;
        this.loyaltyService = loyaltyService;
        this.incidentScoreService = incidentScoreService;
        this.fraudCheckService = fraudCheckService;
    }

    @Transactional
    public void confirmPolicyPurchase(Policy policy, Users customer) {

        double calculatedPremium = calculatePremium(policy, customer);
        policy.setTotalPremium(calculatedPremium);

        if (!customer.getFraudStatus()){
            boolean possibleFraud = fraudCheckService.fraudCheck(customer.getId());
            if (possibleFraud) {
                customer.setFraudStatus(true);
                userService.updateUser(customer);
            }
        }
// I can't decide if I need to check again If the customer once has been fraudulent
//            boolean possibleFraud = fraudCheckService.fraudCheck(customer.getId());
//        System.out.println("possibleFraud:");
//        System.out.println(possibleFraud);
//            if (possibleFraud) {
//                customer.setFraudStatus(true);
//                userService.updateUser(customer);
//            } else {
//                customer.setFraudStatus(false);
//                userService.updateUser(customer);
//        }

        if (customer.getFraudStatus()) {
            policy.setStatus("UR");
        } else {
            policy.setStatus("P");
        }
        policyService.savePolicy(policy);
    }

    @Transactional
    public void calculatePolicyPremium(Policy policy, Users customer) {

        double calculatedPremium = calculatePremium(policy, customer);
        policy.setTotalPremium(calculatedPremium);

        policyService.savePolicy(policy);
    }


    private double calculatePremium(Policy policy, Users customer) {
        double sumInsured = policy.getSum_insured();
        double totalRiskPremium = 0;

        List<Policy_risk> policyRisks = policyRiskService.findByPolicyID(policy.getPolicy_id());

        for (Policy_risk policyRisk : policyRisks) {
            Risk risk = riskRepository.findById(policyRisk.getRisk().getRisk_id())
                    .orElseThrow(() -> new EntityNotFoundException("Risk not found"));

            double riskPremium = sumInsured * risk.getRate();
            totalRiskPremium += riskPremium;
        }

        double l_conditionRate = 1.0;
        double l_incidentScore = incidentScoreService.calculateIncidentScore(customer.getId());
        double l_loyaltyLevel = loyaltyService.calculateLoyalty(customer);

        if (l_loyaltyLevel > 0) {
            l_conditionRate -= (l_loyaltyLevel * 0.05); // For each loyalty point, 5% discount
        }

        if (l_incidentScore > 0) {
            l_conditionRate += (l_incidentScore / 10); // to add 10% of IncidentScore ratings %
        }

        double l_totalPremium = totalRiskPremium * l_conditionRate;

        l_totalPremium = l_totalPremium * policy.getDuration();

        return l_totalPremium;
    }

}


