package com.project.InsuranceProject.data.repositories;

import com.project.InsuranceProject.data.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {
}
