package com.project.InsuranceProject.data.repositories;

import com.project.InsuranceProject.data.entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {
	List<Claim> findByStatus(String status);
}
