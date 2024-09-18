package com.project.InsuranceProject.data.logic;

import com.project.InsuranceProject.data.entity.*;
import com.project.InsuranceProject.data.repositories.RiskRepository;
import com.project.InsuranceProject.data.services.*;
import com.project.InsuranceProject.data.services.pdf.PDFService;
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

    private final PDFService pdfService;

    public PolicyLogic(UserService userService, PolicyService policyService, PolicyRiskService policyRiskService,
                       RiskRepository riskRepository, LoyaltyService loyaltyService, IncidentScoreService incidentScoreService,
                       FraudCheckService fraudCheckService, PDFService pdfService, PDFService pdfService1) {
        this.userService = userService;
        this.policyService = policyService;
        this.policyRiskService = policyRiskService;
        this.riskRepository = riskRepository;
        this.loyaltyService = loyaltyService;
        this.incidentScoreService = incidentScoreService;
        this.fraudCheckService = fraudCheckService;
        this.pdfService = pdfService1;
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

        if (customer.getFraudStatus()) {
            policy.setStatus("UR");
        } else {
            policy.setStatus("P");
        }
        policyService.savePolicy(policy);
        pdfService.saveDocumentToDatabase(policy);
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


