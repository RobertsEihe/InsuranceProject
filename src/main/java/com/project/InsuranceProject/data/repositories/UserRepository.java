package com.project.InsuranceProject.data.repositories;
import com.project.InsuranceProject.data.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);
    Optional<Users> findByEmail(String email);

    List<Users> findByRole(String role);

    boolean existsByUsername(String username);
}