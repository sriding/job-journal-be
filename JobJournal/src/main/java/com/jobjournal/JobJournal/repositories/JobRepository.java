package com.jobjournal.JobJournal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobjournal.JobJournal.shared.models.entity.Job;

public interface JobRepository extends JpaRepository<Job, Long> {

}
