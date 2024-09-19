package com.project.InsuranceProject.data.services;

import com.project.InsuranceProject.data.entity.Policy;
import com.project.InsuranceProject.data.entity.Policy_risk;
import com.project.InsuranceProject.data.repositories.PolicyRepository;
import com.project.InsuranceProject.data.repositories.PolicyRiskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AllPoliciesTest {

    @Mock
    private PolicyRepository policyRepository;

    @Mock
    private PolicyRiskRepository policyRiskRepository;

    @InjectMocks
    private PolicyRetrieveService policyRetrieveService;

    @InjectMocks
    private PolicyRiskService policyRiskService;

    @InjectMocks
    private PolicyService policyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // PolicyRetrieveService tests
    @Test
    void testGetPolicyCustomer() {
        long userId = 1L;
        List<Policy> expectedPolicies = Arrays.asList(new Policy(), new Policy());
        when(policyRepository.findByUsers_Id(userId)).thenReturn(expectedPolicies);

        List<Policy> result = policyRetrieveService.getPolicyCustomer(userId);

        assertEquals(expectedPolicies, result);
        verify(policyRepository).findByUsers_Id(userId);
    }

    @Test
    void testGetPolicyByUsername() {
        String username = "testUser";
        List<Policy> expectedPolicies = Arrays.asList(new Policy(), new Policy());
        when(policyRepository.findByUsers_Username(username)).thenReturn(expectedPolicies);

        List<Policy> result = policyRetrieveService.getPolicyByUsername(username);

        assertEquals(expectedPolicies, result);
        verify(policyRepository).findByUsers_Username(username);
    }

    @Test
    void testSavePolicyInPolicyRetrieveService() {
        Policy policy = new Policy();
        when(policyRepository.save(policy)).thenReturn(policy);

        Policy result = policyRetrieveService.savePolicy(policy);

        assertEquals(policy, result);
        verify(policyRepository).save(policy);
    }

    @Test
    void testGetPoliciesByAgentId() {
        Long agentId = 1L;
        List<Policy> expectedPolicies = Arrays.asList(new Policy(), new Policy());
        when(policyRepository.findByAgentId(agentId)).thenReturn(expectedPolicies);

        List<Policy> result = policyRetrieveService.getPoliciesByAgentId(agentId);

        assertEquals(expectedPolicies, result);
        verify(policyRepository).findByAgentId(agentId);
    }

    @Test
    void testApprovePolicyInPolicyRetrieveService() {
        Policy policy = new Policy();
        policyRetrieveService.approvePolicy(policy);

        assertEquals("APPROVED", policy.getUrStatus());
        assertEquals("P", policy.getStatus());
        verify(policyRepository).save(policy);
    }

    @Test
    void testDenyPolicyInPolicyRetrieveService() {
        Policy policy = new Policy();
        policyRetrieveService.denyPolicy(policy);

        assertEquals("DENIED", policy.getUrStatus());
        assertEquals("D", policy.getStatus());
        verify(policyRepository).save(policy);
    }

    @Test
    void testConfirmPolicy() {
        Policy policy = new Policy();
        policyRetrieveService.confirmPolicy(policy);

        assertEquals("UR", policy.getStatus());
        verify(policyRepository).save(policy);
    }

    // PolicyRiskService tests
    @Test
    void testSavePolicyRisk() {
        Policy_risk policyRisk = new Policy_risk();
        when(policyRiskRepository.save(policyRisk)).thenReturn(policyRisk);

        Policy_risk result = policyRiskService.savePolicyRisk(policyRisk);

        assertEquals(policyRisk, result);
        verify(policyRiskRepository).save(policyRisk);
    }

    @Test
    void testFindByPolicyID() {
        Long policyId = 1L;
        List<Policy_risk> expectedRisks = Arrays.asList(new Policy_risk(), new Policy_risk());
        when(policyRiskRepository.findByPolicyID(policyId)).thenReturn(expectedRisks);

        List<Policy_risk> result = policyRiskService.findByPolicyID(policyId);

        assertEquals(expectedRisks, result);
        verify(policyRiskRepository).findByPolicyID(policyId);
    }

    @Test
    void testGetPolicyRiskByUsername() {
        String username = "testUser";
        List<Policy_risk> expectedRisks = Arrays.asList(new Policy_risk(), new Policy_risk());
        when(policyRiskRepository.getPolicyRiskByUsername(username)).thenReturn(expectedRisks);

        List<Policy_risk> result = policyRiskService.getPolicyRiskByUsername(username);

        assertEquals(expectedRisks, result);
        verify(policyRiskRepository).getPolicyRiskByUsername(username);
    }

    // PolicyService tests
    @Test
    void testGetPoliciesUnderReview() {
        List<Policy> expectedPolicies = Arrays.asList(new Policy(), new Policy());
        when(policyRepository.findByUrStatus("UR")).thenReturn(expectedPolicies);

        List<Policy> result = policyService.getPoliciesUnderReview();

        assertEquals(expectedPolicies, result);
        verify(policyRepository).findByUrStatus("UR");
    }

    @Test
    void testSearchPolicies() {
        String searchText = "test";
        List<Policy> expectedPolicies = Arrays.asList(new Policy(), new Policy());
        when(policyRepository.searchPolicies(searchText)).thenReturn(expectedPolicies);

        List<Policy> result = policyService.searchPolicies(searchText);

        assertEquals(expectedPolicies, result);
        verify(policyRepository).searchPolicies(searchText);
    }

    @Test
    void testApprovePolicyInPolicyService() {
        Policy policy = new Policy();
        policyService.approvePolicy(policy);

        assertEquals("APPROVED", policy.getUrStatus());
        assertEquals("P", policy.getStatus());
        verify(policyRepository).save(policy);
    }

    @Test
    void testDenyPolicyInPolicyService() {
        Policy policy = new Policy();
        policyService.denyPolicy(policy);

        assertEquals("DENIED", policy.getUrStatus());
        verify(policyRepository).save(policy);
    }

    @Test
    void testGetAllPolicies() {
        List<Policy> expectedPolicies = Arrays.asList(new Policy(), new Policy());
        when(policyRepository.findAll()).thenReturn(expectedPolicies);

        List<Policy> result = policyService.getAllPolicies();

        assertEquals(expectedPolicies, result);
        verify(policyRepository).findAll();
    }

    @Test
    void testSavePolicyInPolicyService() {
        Policy policy = new Policy();
        when(policyRepository.save(policy)).thenReturn(policy);

        Policy result = policyService.savePolicy(policy);

        assertEquals(policy, result);
        verify(policyRepository).save(policy);
    }
}