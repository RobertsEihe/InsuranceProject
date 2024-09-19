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

        // Mock the fraud check service to return false (no fraud detected)
        when(fraudCheckService.fraudCheck(customer.getId())).thenReturn(false);

        // Mocking the premium calculation logic
        when(policyRiskService.findByPolicyID(policy.getPolicy_id())).thenReturn(new ArrayList<>());
        when(incidentScoreService.calculateIncidentScore(customer.getId())).thenReturn(0);
        when(loyaltyService.calculateLoyalty(customer)).thenReturn(0);

        // Mock the behavior of PDFService
        doNothing().when(pdfService).saveDocumentToDatabase(policy);

        // Call the method
        policyLogic.confirmPolicyPurchase(policy, customer);

        // Verify that fraudCheckService.fraudCheck was called
        verify(fraudCheckService).fraudCheck(customer.getId());

        // Assert that the policy status is set to "P" (purchased)
        assertEquals("P", policy.getStatus());

        // Verify that the policy was saved
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

        // Mock the fraud check service to return true (fraud detected)
        when(fraudCheckService.fraudCheck(customer.getId())).thenReturn(true);

        // Mock the premium calculation logic
        when(policyRiskService.findByPolicyID(policy.getPolicy_id())).thenReturn(new ArrayList<>());
        when(incidentScoreService.calculateIncidentScore(customer.getId())).thenReturn(0);
        when(loyaltyService.calculateLoyalty(customer)).thenReturn(0);

        // Call the method
        policyLogic.confirmPolicyPurchase(policy, customer);

        // Verify that fraudCheckService.fraudCheck was called
        verify(fraudCheckService).fraudCheck(customer.getId());

        // Assert that the customer's fraud status was updated and policy status is "UR" (under review)
        assertTrue(customer.getFraudStatus());
        assertEquals("UR", policy.getStatus());

        // Verify that the customer and policy were saved
        verify(userService).updateUser(customer);
        verify(policyService).savePolicy(policy);
    }

    @Test
    public void testCalculatePolicyPremium() {
        Users customer = new Users();
        customer.setId(1L);

        Policy policy = new Policy();
        policy.setDuration(2);
        policy.setSum_insured(100000);
        policy.setPolicy_id(1L);

        List<Policy_risk> policyRisks = new ArrayList<>();
        Policy_risk policyRisk = new Policy_risk();
        Risk risk = new Risk();
        risk.setRisk_id(1L);
        risk.setRate(0.05);  // 5% risk rate
        policyRisk.setRisk(risk);
        policyRisks.add(policyRisk);

        // Mock the policy risks and risk repository
        when(policyRiskService.findByPolicyID(policy.getPolicy_id())).thenReturn(policyRisks);
        when(riskRepository.findById(1L)).thenReturn(Optional.of(risk));

        // Mock the loyalty and incident score services
        when(loyaltyService.calculateLoyalty(customer)).thenReturn(1); // 5% discount
        when(incidentScoreService.calculateIncidentScore(customer.getId())).thenReturn(2); // 20% incident score

        // Call the method
        policyLogic.calculatePolicyPremium(policy, customer);

        // Assert that the premium is calculated correctly
        assertEquals(11500.0, policy.getTotalPremium(), 0.01); // Expected premium after all calculations

        // Verify that the policy was saved
        verify(policyService).savePolicy(policy);
    }

}