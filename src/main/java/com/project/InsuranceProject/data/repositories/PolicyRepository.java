package com.project.InsuranceProject.data.repositories;

import com.project.InsuranceProject.data.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {

	// Query to find policies under review (ur_status)
	List<Policy> findByUrStatus(String urStatus);

	// Custom query to search policies by username or status
	@Query("SELECT p FROM Policy p WHERE p.users.name LIKE %?1% OR p.status LIKE %?1%")
	List<Policy> searchPolicies(String searchText);
}
