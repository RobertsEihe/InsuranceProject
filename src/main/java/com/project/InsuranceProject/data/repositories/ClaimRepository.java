package com.project.InsuranceProject.data.repositories;

import com.project.InsuranceProject.data.entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {
    // You can add custom query methods here if needed
    // For example:
    // Optional<Claim> findByName(String name);
}