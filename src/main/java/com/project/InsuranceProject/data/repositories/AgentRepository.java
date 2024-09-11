package com.project.InsuranceProject.data.repositories;

import com.project.InsuranceProject.data.entity.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {
    // You can add custom query methods here if needed
    // For example:
    // Optional<Agent> findByName(String name);
}