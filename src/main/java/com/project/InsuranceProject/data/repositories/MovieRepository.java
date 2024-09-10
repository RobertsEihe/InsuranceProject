package com.project.InsuranceProject.data.repositories;

import com.project.InsuranceProject.data.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    // You can add custom query methods here if needed
    // For example:
    // List<Movie> findByReleaseYear(int year);
    // List<Movie> findByDirectorName(String directorName);
}