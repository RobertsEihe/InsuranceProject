package com.project.InsuranceProject.data.services;

import com.project.InsuranceProject.data.entity.Claim;
import com.project.InsuranceProject.data.repositories.ClaimRepository;
import com.project.InsuranceProject.data.repositories.PolicyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ClaimTest {

    @Mock
    private ClaimRepository claimRepository;

    @Mock
    private PolicyRepository policyRepository;

    private ClaimService claimService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        claimService = new ClaimService(claimRepository, policyRepository);
    }

    @Test
    void testApproveClaim() {
        Claim claim = new Claim();
        claim.setStatus("UR");
        claimService.approveClaim(claim);

        assertEquals("APPROVED", claim.getStatus());
        verify(claimRepository, times(1)).save(claim);
    }

    @Test
    void testDenyClaim() {

        Claim claim = new Claim();
        claim.setStatus("UR");
        claimService.denyClaim(claim);

        assertEquals("DENIED", claim.getStatus());
        verify(claimRepository, times(1)).save(claim);
    }

    @Test
    void testGetClaimsByUsername() {

        String username = "testUser";
        Claim claim1 = new Claim();
        Claim claim2 = new Claim();
        List<Claim> expectedClaims = Arrays.asList(claim1, claim2);

        when(claimRepository.findByPolicyUsersUsername(username)).thenReturn(expectedClaims);

        List<Claim> result = claimService.getClaimsByUsername(username);

        assertEquals(expectedClaims, result);
        verify(claimRepository, times(1)).findByPolicyUsersUsername(username);
    }

    @Test
    void testSaveNewClaim() {
        Claim claim = new Claim();

        claimService.saveClaim(claim);

        assertEquals("UR", claim.getStatus());
        verify(claimRepository, times(1)).save(claim);
    }

    @Test
    void testSaveExistingClaim() {
        Claim claim = new Claim();
        claim.setClaim_id(1L);
        claim.setStatus("APPROVED");

        claimService.saveClaim(claim);

        assertEquals("APPROVED", claim.getStatus());
        verify(claimRepository, times(1)).save(claim);
    }
}