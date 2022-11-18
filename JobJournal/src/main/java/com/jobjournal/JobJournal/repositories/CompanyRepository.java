package com.jobjournal.JobJournal.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jobjournal.JobJournal.shared.models.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Query(value = "SELECT * FROM company WHERE post_id = ?1", nativeQuery = true)
    Optional<Company> findCompanyByPostId(Long postId);

    @Query(value = "DELETE FROM company WHERE post_id = ?1", nativeQuery = true)
    void deleteCompanyByPostId(Long postId);
}
