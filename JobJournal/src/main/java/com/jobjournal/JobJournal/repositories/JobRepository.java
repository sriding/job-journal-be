package com.jobjournal.JobJournal.repositories;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.jobjournal.JobJournal.shared.models.entity.Job;

@Transactional
public interface JobRepository extends JpaRepository<Job, Long> {
    @Query(value = "SELECT * FROM job WHERE post_id = ?1", nativeQuery = true)
    Optional<Job> getJobByPostId(Long postId);

    @Modifying
    @Query(value = "DELETE FROM job WHERE post_id = ?1", nativeQuery = true)
    void deleteJobByPostId(Long postId);
}
