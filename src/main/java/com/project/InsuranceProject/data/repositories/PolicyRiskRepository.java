package com.project.InsuranceProject.data.repositories;

import com.project.InsuranceProject.data.entity.Policy_risk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PolicyRiskRepository extends JpaRepository<Policy_risk, Long> {

    @Query("SELECT p FROM Policy_risk p WHERE p.policy.id = ?1")
    List<Policy_risk> findByPolicyID(Long id);


    @Query("SELECT p FROM Policy_risk p WHERE p.policy.users.username = ?1")
    List<Policy_risk> getPolicyRiskByUsername(String username);

    @Query("SELECT DISTINCT r.risk FROM Policy_risk pr JOIN pr.risk r WHERE pr.policy.policy_id = ?1")
    List<String> findRiskTypesByPolicyId(Long policyId);
}