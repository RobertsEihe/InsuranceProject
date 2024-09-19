package com.project.InsuranceProject.data.logic;

import com.project.InsuranceProject.data.entity.*;
import com.project.InsuranceProject.data.repositories.*;
import com.project.InsuranceProject.data.services.*;
import com.project.InsuranceProject.data.services.pdf.PDFService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PolicyLogicTest {

    @Mock
    private UserService userService;

    @Mock
    private PolicyService policyService;

    @Mock
    private PolicyRiskService policyRiskService;

    @Mock
    private RiskRepository riskRepository;

    @Mock
    private LoyaltyService loyaltyService;

    @Mock
    private IncidentScoreService incidentScoreService;

    @Mock
    private FraudCheckService fraudCheckService;

    @Mock
    private PDFService pdfService;

    @InjectMocks
    private PolicyLogic policyLogic;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void confirmPolicyPurchase() {
    }

    @Test
    void calculatePolicyPremium() {
    }

    @Test
    public void testConfirmPolicyPurchase_CustomerNotFraudulent() {
        Users customer = new Users();
        customer.setId(1L);
        customer.setFraudStatus(false);

        Policy policy = new Policy();
        policy.setDuration(1);
        policy.setSum_insured(100000);
        policy.setPolicy_id(1L);

        when(fraudCheckService.fraudCheck(customer.getId())).thenReturn(false);

        when(policyRiskService.findByPolicyID(policy.getPolicy_id())).thenReturn(new ArrayList<>());
        when(incidentScoreService.calculateIncidentScore(customer.getId())).thenReturn(0);
        when(loyaltyService.calculateLoyalty(customer)).thenReturn(1);

        doNothing().when(pdfService).saveDocumentToDatabase(policy);

        policyLogic.confirmPolicyPurchase(policy, customer);

        verify(fraudCheckService).fraudCheck(customer.getId());

        assertEquals("P", policy.getStatus());

        verify(policyService).savePolicy(policy);
    }

    @Test
    public void testConfirmPolicyPurchase_CustomerFraudulent() {
        Users customer = new Users();
        customer.setId(1L);
        customer.setFraudStatus(false);

        Policy policy = new Policy();
        policy.setDuration(1);
        policy.setSum_insured(100000);
        policy.setPolicy_id(1L);

        when(fraudCheckService.fraudCheck(customer.getId())).thenReturn(true);

        when(policyRiskService.findByPolicyID(policy.getPolicy_id())).thenReturn(new ArrayList<>());
        when(incidentScoreService.calculateIncidentScore(customer.getId())).thenReturn(0);
        when(loyaltyService.calculateLoyalty(customer)).thenReturn(1);

        policyLogic.confirmPolicyPurchase(policy, customer);

        verify(fraudCheckService).fraudCheck(customer.getId());

        assertTrue(customer.getFraudStatus());
        assertEquals("UR", policy.getStatus());

        // Verify that the customer and policy were saved(not saved for real)
        verify(userService).updateUser(customer);
        verify(policyService).savePolicy(policy);
    }

    @Test
    public void testCalculatePolicyPremium() {
        Users customer = new Users();
        customer.setId(1L);

        Policy policy = new Policy();
        policy.setDuration(2);
        policy.setSum_insured(10000);
        policy.setPolicy_id(1L);

        List<Policy_risk> policyRisks = new ArrayList<>();
        Policy_risk policyRisk = new Policy_risk();
        Risk risk = new Risk();
        risk.setRisk_id(1L);
        risk.setRate(0.01);  // risk rate for Crash risk
        policyRisk.setRisk(risk);
        policyRisks.add(policyRisk);

        when(policyRiskService.findByPolicyID(policy.getPolicy_id())).thenReturn(policyRisks);
        when(riskRepository.findById(1L)).thenReturn(Optional.of(risk));

        when(loyaltyService.calculateLoyalty(customer)).thenReturn(1);
        when(incidentScoreService.calculateIncidentScore(customer.getId())).thenReturn(2); // 20% incident score

        policyLogic.calculatePolicyPremium(policy, customer);

        assertEquals(230.0, Math.round(policy.getTotalPremium()), 0.01);

        verify(policyService).savePolicy(policy);
    }

}