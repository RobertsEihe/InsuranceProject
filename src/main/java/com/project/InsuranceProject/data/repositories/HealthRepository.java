package com.project.InsuranceProject.data.repositories;

import com.project.InsuranceProject.data.entity.Health;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthRepository extends JpaRepository<Health, Long> {
}