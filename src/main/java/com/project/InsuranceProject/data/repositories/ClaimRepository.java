package com.project.InsuranceProject.data.repositories;

import com.project.InsuranceProject.data.entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {
	List<Claim> findByStatus(String status);
	@Query("SELECT c FROM Claim c WHERE c.policy.users.username = :username")
	List<Claim> findByPolicyUsersUsername(String username);

	@Query("SELECT c FROM Claim c WHERE c.policy.users.id = :user_id")
	List<Claim> findByUserId(Long user_id);
}
