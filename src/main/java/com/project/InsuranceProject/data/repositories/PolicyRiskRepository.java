package com.project.InsuranceProject.data.repositories;

import com.project.InsuranceProject.data.entity.Policy_risk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface PolicyRiskRepository extends JpaRepository<Policy_risk, Long> {
}