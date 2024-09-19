package com.project.InsuranceProject.data.services;

import com.project.InsuranceProject.data.entity.*;
import com.project.InsuranceProject.data.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeViewService {

    @Autowired private PolicyRepository policyRepository;
    @Autowired private RiskRepository riskRepository;
    @Autowired private VehicleRepository vehicleRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private DocumentRepository documentRepository;
    @Autowired private ClaimRepository claimRepository;
// here changes will be made later to view more details about each thing etc...
    public List<Policy> getAllPolicies() {
        return policyRepository.findAll();
    }

    public List<Risk> getAllRisks() {
        return riskRepository.findAll();
    }

    public Optional<Risk> getRiskById(Long id) {
        return riskRepository.findById(id);
    }

    @Transactional
    public Risk saveRisk(Risk risk) {
        return riskRepository.save(risk);
    }

    @Transactional
    public Risk updateRisk(Risk risk) {
        if (riskRepository.existsById(risk.getRisk_id())) {
            return riskRepository.save(risk);
        } else {
            throw new RuntimeException("Risk not found with id: " + risk.getRisk_id());
        }
    }

    @Transactional
    public void deleteRisk(Long id) {
        riskRepository.deleteById(id);
    }

    public List<Risk> getRisksByType(String type) {
        return riskRepository.findByType(type);
    }
    @Transactional
    public Risk addRisk(Risk risk) {
        return riskRepository.save(risk);
    }


}