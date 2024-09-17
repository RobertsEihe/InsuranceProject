package com.project.InsuranceProject.data.repositories;
import com.project.InsuranceProject.data.entity.Risk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RiskRepository extends JpaRepository<Risk, Long> {

    List<Risk> findByType(String type);
    Optional<Risk> findByRiskAndType(String name, String type);
}