
package com.project.InsuranceProject.data.repositories;
import com.project.InsuranceProject.data.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
}