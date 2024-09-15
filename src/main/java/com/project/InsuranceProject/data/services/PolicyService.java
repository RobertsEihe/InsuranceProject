package com.project.InsuranceProject.data.services;

import com.project.InsuranceProject.data.entity.Policy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PolicyService {

	// Temporary data store for mock policies
	private List<Policy> mockPolicies = new ArrayList<>();

	public PolicyService() {
		// Add some mock data for display purposes
		mockPolicies.add(new Policy(LocalDate.now(), LocalDate.now().plusMonths(6), 6, false, "UR", "PENDING", null));  // Adjust the constructor as per your Policy entity
		mockPolicies.add(new Policy(LocalDate.now(), LocalDate.now().plusMonths(12), 12, true, "P", "APPROVED", null));
	}

	// Temporary method to simulate fetching policies under review
	@Transactional(readOnly = true)
	public List<Policy> getPoliciesUnderReview() {
		// Return only policies with status "UR" (Under Review)
		List<Policy> policiesUnderReview = new ArrayList<>();
		for (Policy policy : mockPolicies) {
			if ("UR".equals(policy.getUr_status())) {
				policiesUnderReview.add(policy);
			}
		}
		return policiesUnderReview;
	}

	// Temporary method to simulate policy search
	@Transactional(readOnly = true)
	public List<Policy> searchPolicies(String searchText) {
		// Return policies based on a searchText match in status or other fields
		List<Policy> searchedPolicies = new ArrayList<>();
		for (Policy policy : mockPolicies) {
			if (policy.getStatus().contains(searchText) || policy.getUr_status().contains(searchText)) {
				searchedPolicies.add(policy);
			}
		}
		return searchedPolicies;
	}

	// Temporary method to simulate approving a policy
	@Transactional
	public void approvePolicy(Policy policy) {
		// Simulate setting the policy status to "APPROVED"
		policy.setStatus("APPROVED");
		policy.setUr_status("P");  // Change to 'P' (Policy) after approval
	}

	// Temporary method to simulate denying a policy
	@Transactional
	public void denyPolicy(Policy policy) {
		// Simulate setting the policy status to "DENIED"
		policy.setStatus("DENIED");
		policy.setUr_status("DENIED");  // Set appropriate status
	}
}
