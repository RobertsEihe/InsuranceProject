package com.project.InsuranceProject.data.repositories;

import com.project.InsuranceProject.data.entity.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {
    // You can add custom query methods here if needed
    // For example:
    // Optional<Director> findByName(String name);
}