package com.project.InsuranceProject.data.repositories;

import com.project.InsuranceProject.data.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // You can add custom query methods here if needed
    // For example:
    // Optional<Customer> findByName(String name);
}