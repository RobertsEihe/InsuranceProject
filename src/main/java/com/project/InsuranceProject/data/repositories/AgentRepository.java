package com.project.InsuranceProject.data.repositories;

import com.project.InsuranceProject.data.entity.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {
}