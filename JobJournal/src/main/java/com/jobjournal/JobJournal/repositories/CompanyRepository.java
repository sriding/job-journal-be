package com.jobjournal.JobJournal.repositories;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.jobjournal.JobJournal.shared.models.entity.Company;

@Transactional
public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Query(value = "SELECT * FROM company WHERE _post_id_fk_company = ?1", nativeQuery = true)
    Optional<Company> findCompanyByPostId(Long _post_id);

    @Query(value = "SELECT _company_id FROM company WHERE _post_id_fk_company = ?1", nativeQuery = true)
    Optional<Long> findCompanyIdByPostId(Long _post_id);

    @Modifying
    @Query(value = "DELETE FROM company WHERE _post_id_fk_company = ?1", nativeQuery = true)
    int deleteCompanyByPostId(Long _post_id);
}
