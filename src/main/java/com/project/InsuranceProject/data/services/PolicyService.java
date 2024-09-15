package com.project.InsuranceProject.data.services;

import com.project.InsuranceProject.data.entity.Policy;
import java.time.LocalDate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PolicyService {

	private List<Policy> mockPolicies = new ArrayList<>();

	public PolicyService() {
		// Add some mock data for display
		mockPolicies.add(new Policy(LocalDate.now(), LocalDate.now().plusMonths(6), 6, false, "UR", "PENDING", null));  // Adjust constructor as needed
		mockPolicies.add(new Policy(LocalDate.now(), LocalDate.now().plusMonths(12), 12, true, "P", "APPROVED", null));
	}

	@Transactional(readOnly = true)
	public List<Policy> getPoliciesUnderReview() {
		// Return an empty list or mock data
		return mockPolicies;
	}

	@Transactional(readOnly = true)
	public List<Policy> searchPolicies(String searchText) {
		// Return an empty list or mock data based on search
		return mockPolicies;
	}

	// Mock approvePolicy method
	@Transactional
	public void approvePolicy(Policy policy) {
		// Simulate policy approval (you can also update the policy object in mock data)
		policy.setStatus("APPROVED");
	}

	// Mock denyPolicy method
	@Transactional
	public void denyPolicy(Policy policy) {
		// Simulate policy denial (you can also update the policy object in mock data)
		policy.setStatus("DENIED");
	}
}
