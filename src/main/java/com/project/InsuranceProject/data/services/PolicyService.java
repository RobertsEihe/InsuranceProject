package com.project.InsuranceProject.data.services;

import com.project.InsuranceProject.data.entity.Policy;
import com.project.InsuranceProject.data.repositories.PolicyRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PolicyService {

	private final PolicyRepository policyRepository;

	@Autowired
	public PolicyService(PolicyRepository policyRepository) {
		this.policyRepository = policyRepository;
	}

	// Fetch all policies that are under review
	@Transactional(readOnly = true)
	public List<Policy> getPoliciesUnderReview() {
		List<Policy> policies = policyRepository.findByUrStatus("UR");

		// Manually initialize lazy-loaded properties
		policies.forEach(policy -> {
			Hibernate.initialize(policy.getClaims());  // This forces initialization of the claims collection
		});
		return policies;
	}


	// Search policies by search text (name or status)
	@Transactional(readOnly = true)
	public List<Policy> searchPolicies(String searchText) {
		return policyRepository.searchPolicies(searchText);
	}

	// Approve a policy
	@Transactional
	public void approvePolicy(Policy policy) {
		policy.setUrStatus("APPROVED");
		policyRepository.save(policy);
	}

	// Deny a policy
	@Transactional
	public void denyPolicy(Policy policy) {
		policy.setUrStatus("DENIED");
		policyRepository.save(policy);
	}

	// Fetch all policies
	@Transactional(readOnly = true)
	public List<Policy> getAllPolicies() {
		List<Policy> policies = policyRepository.findAll();
		policies.forEach(policy -> Hibernate.initialize(policy.getUsers()));  // Initialize users collection
		return policies;
	}
}
