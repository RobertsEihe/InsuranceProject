package com.project.InsuranceProject.data.services;

import com.project.InsuranceProject.data.entity.*;
import com.project.InsuranceProject.data.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeViewService {

    @Autowired private PolicyRepository policyRepository;
    @Autowired private RiskRepository riskRepository;
    @Autowired private HouseRepository houseRepository;
    @Autowired private VehicleRepository vehicleRepository;
    @Autowired private HealthRepository healthRepository;
    @Autowired private PaymentRepository paymentRepository;
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

    public List<House> getAllHouses() {
        return houseRepository.findAll();
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public List<Health> getAllHealth() {
        return healthRepository.findAll();
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    public List<Claim> getAllClaims() {
        return claimRepository.findAll();
    }
}