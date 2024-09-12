//package com.project.InsuranceProject.data.services;
//
//import com.project.InsuranceProject.data.entity.Agent;
//import com.project.InsuranceProject.data.entity.Policy;
//import com.project.InsuranceProject.data.repositories.AgentRepository;
//import com.project.InsuranceProject.data.repositories.PolicyRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class AgentPolicyService {
//
//	@Autowired
//	private PolicyRepository policyRepository;
//
//	@Autowired
//	private AgentRepository agentRepository;
//
//	// Get all policies in "Under Review" state
//	public List<Policy> getPoliciesUnderReview() {
//		return policyRepository.findByStatus("UR");
//	}
//
//	// Approve a policy and assign the agent
//	public void approvePolicy(Long policyId, Long agentId) {
//		Policy policy = policyRepository.findById(policyId)
//				.orElseThrow(() -> new RuntimeException("Policy not found"));
//		Agent agent = agentRepository.findById(agentId)
//				.orElseThrow(() -> new RuntimeException("Agent not found"));
//
//		policy.setUr_status("APPROVED");
//		policy.setAgent(agent); // Assign the agent
//		policyRepository.save(policy); // Save changes
//	}
//
//	// Deny a policy and assign the agent
//	public void denyPolicy(Long policyId, Long agentId) {
//		Policy policy = policyRepository.findById(policyId)
//				.orElseThrow(() -> new RuntimeException("Policy not found"));
//		Agent agent = agentRepository.findById(agentId)
//				.orElseThrow(() -> new RuntimeException("Agent not found"));
//
//		policy.setUr_status("DENIED");
//		policy.setAgent(agent); // Assign the agent
//		policyRepository.save(policy); // Save changes
//	}
//}
//
