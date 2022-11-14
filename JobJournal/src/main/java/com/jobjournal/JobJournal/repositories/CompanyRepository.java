package com.jobjournal.JobJournal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobjournal.JobJournal.shared.models.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}
