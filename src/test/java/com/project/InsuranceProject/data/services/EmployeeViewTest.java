package com.project.InsuranceProject.data.services;

import com.project.InsuranceProject.data.entity.Risk;
import com.project.InsuranceProject.data.entity.Policy;
import com.project.InsuranceProject.data.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeViewTest {

    @Mock
    private PolicyRepository policyRepository;
    @Mock
    private RiskRepository riskRepository;
    @Mock
    private HouseRepository houseRepository;
    @Mock
    private VehicleRepository vehicleRepository;
    @Mock
    private HealthRepository healthRepository;
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private DocumentRepository documentRepository;
    @Mock
    private ClaimRepository claimRepository;

    @InjectMocks
    private EmployeeViewService employeeViewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPolicies() {

        List<Policy> expectedPolicies = Arrays.asList(new Policy(), new Policy());
        when(policyRepository.findAll()).thenReturn(expectedPolicies);

        List<Policy> result = employeeViewService.getAllPolicies();

        assertEquals(expectedPolicies, result);
        verify(policyRepository, times(1)).findAll();
    }

    @Test
    void testGetAllRisks() {

        List<Risk> expectedRisks = Arrays.asList(new Risk(), new Risk());
        when(riskRepository.findAll()).thenReturn(expectedRisks);

        List<Risk> result = employeeViewService.getAllRisks();

        assertEquals(expectedRisks, result);
        verify(riskRepository, times(1)).findAll();
    }

    @Test
    void testGetRiskById() {

        Long riskId = 1L;
        Risk expectedRisk = new Risk();
        when(riskRepository.findById(riskId)).thenReturn(Optional.of(expectedRisk));

        Optional<Risk> result = employeeViewService.getRiskById(riskId);

        assertTrue(result.isPresent());
        assertEquals(expectedRisk, result.get());
        verify(riskRepository, times(1)).findById(riskId);
    }

    @Test
    void testSaveRisk() {

        Risk riskToSave = new Risk();
        when(riskRepository.save(riskToSave)).thenReturn(riskToSave);

        Risk result = employeeViewService.saveRisk(riskToSave);

        assertEquals(riskToSave, result);
        verify(riskRepository, times(1)).save(riskToSave);
    }

    @Test
    void testUpdateRiskSuccess() {

        Risk riskToUpdate = new Risk();
        riskToUpdate.setRisk_id(1L);
        when(riskRepository.existsById(1L)).thenReturn(true);
        when(riskRepository.save(riskToUpdate)).thenReturn(riskToUpdate);

        Risk result = employeeViewService.updateRisk(riskToUpdate);

        assertEquals(riskToUpdate, result);
        verify(riskRepository, times(1)).existsById(1L);
        verify(riskRepository, times(1)).save(riskToUpdate);
    }

    @Test
    void testUpdateRiskNotFound() {
        Risk riskToUpdate = new Risk();
        riskToUpdate.setRisk_id(1L);
        when(riskRepository.existsById(1L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> employeeViewService.updateRisk(riskToUpdate));
        verify(riskRepository, times(1)).existsById(1L);
        verify(riskRepository, never()).save(any(Risk.class));
    }

    @Test
    void testDeleteRisk() {

        Long riskId = 1L;

        employeeViewService.deleteRisk(riskId);

        verify(riskRepository, times(1)).deleteById(riskId);
    }

    @Test
    void testGetRisksByType() {

        String riskType = "Fire";
        List<Risk> expectedRisks = Arrays.asList(new Risk(), new Risk());
        when(riskRepository.findByType(riskType)).thenReturn(expectedRisks);

        List<Risk> result = employeeViewService.getRisksByType(riskType);

        assertEquals(expectedRisks, result);
        verify(riskRepository, times(1)).findByType(riskType);
    }

    @Test
    void testAddRisk() {

        Risk riskToAdd = new Risk();
        when(riskRepository.save(riskToAdd)).thenReturn(riskToAdd);

        Risk result = employeeViewService.addRisk(riskToAdd);

        assertEquals(riskToAdd, result);
        verify(riskRepository, times(1)).save(riskToAdd);
    }
}