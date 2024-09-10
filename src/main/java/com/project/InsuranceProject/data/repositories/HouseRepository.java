package com.project.InsuranceProject.data.repositories;
import com.project.InsuranceProject.data.entity.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {
}
