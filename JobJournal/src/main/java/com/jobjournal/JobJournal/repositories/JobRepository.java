package com.jobjournal.JobJournal.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jobjournal.JobJournal.shared.models.entity.Job;

public interface JobRepository extends JpaRepository<Job, Long> {
    @Query(value = "SELECT * FROM job WHERE post_id = ?1", nativeQuery = true)
    Optional<Job> getJobByPostId(Long postId);

    @Query(value = "DELETE FROM job WHERE post_id = ?1", nativeQuery = true)
    void deleteJobByPostId(Long postId);
}
