
package com.project.InsuranceProject.data.repositories;
import com.project.InsuranceProject.data.entity.Claim;
import com.project.InsuranceProject.data.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    @Query("SELECT d FROM Document d WHERE d.policy.id = :policy_id")
    Optional<Document> findByPolicyId(Long policy_id);
}