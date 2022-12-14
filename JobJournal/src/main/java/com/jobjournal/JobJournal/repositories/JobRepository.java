package com.jobjournal.JobJournal.repositories;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.jobjournal.JobJournal.shared.models.entity.Job;

@Transactional
public interface JobRepository extends JpaRepository<Job, Long> {
    @Query(value = "SELECT * FROM job WHERE _post_id_fk_job = ?1", nativeQuery = true)
    Optional<Job> getJobByPostId(Long _post_id);

    @Query(value = "SELECT _job_id FROM job WHERE _post_id_fk_job = ?1", nativeQuery = true)
    Optional<Long> getJobIdByPostId(Long _post_id);

    @Modifying
    @Query(value = "DELETE FROM job WHERE _post_id_fk_job = ?1", nativeQuery = true)
    int deleteJobByPostId(Long _post_id);
}
