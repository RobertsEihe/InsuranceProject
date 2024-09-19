package com.project.InsuranceProject.data.services;

import com.project.InsuranceProject.data.entity.Claim;
import com.project.InsuranceProject.data.repositories.ClaimRepository;
import com.project.InsuranceProject.data.repositories.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClaimService {

	private final ClaimRepository claimRepository;
	private final PolicyRepository policyRepository;

	@Autowired
	public ClaimService(ClaimRepository claimRepository, PolicyRepository policyRepository) {
		this.claimRepository = claimRepository;
		this.policyRepository = policyRepository;
	}

//	@Transactional(readOnly = true)
//	public List<Claim> getClaimsUnderReview() {
//		return claimRepository.findByStatus("UR");
//	}

	@Transactional
	public void approveClaim(Claim claim) {
		claim.setStatus("APPROVED");
		claimRepository.save(claim);
	}

	@Transactional
	public void denyClaim(Claim claim) {
		claim.setStatus("DENIED");
		claimRepository.save(claim);
	}

//	@Transactional(readOnly = true)
//	public List<Claim> getAllClaims() {
//		return claimRepository.findAll();
//	}
//	// For Customer View to retrieve its claims and ClaimForm
	public List<Claim> getClaimsByUsername(String username) {
		return claimRepository.findByPolicyUsersUsername(username);
	}
	@Transactional
	public void saveClaim(Claim claim) {
		// Set initial status for new claims
		if (claim.getClaim_id() == null) {
			claim.setStatus("UR"); // Under Review
		}
		claimRepository.save(claim);
	}
}

