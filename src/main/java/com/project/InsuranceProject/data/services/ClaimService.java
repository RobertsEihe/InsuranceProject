package com.project.InsuranceProject.data.services;

import com.project.InsuranceProject.data.entity.Claim;
import com.project.InsuranceProject.data.repositories.ClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClaimService {

	private final ClaimRepository claimRepository;

	@Autowired
	public ClaimService(ClaimRepository claimRepository) {
		this.claimRepository = claimRepository;
	}

	@Transactional(readOnly = true)
	public List<Claim> getClaimsUnderReview() {
		return claimRepository.findByStatus("UR");
	}

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

	@Transactional(readOnly = true)
	public List<Claim> getAllClaims() {
		return claimRepository.findAll();
	}
}
