package com.jobjournal.JobJournal.repositories;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.jobjournal.JobJournal.shared.models.entity.UserProfiles;

@Transactional
public interface UserProfilesRepository extends JpaRepository<UserProfiles, Long> {
    @Query(value = "SELECT * FROM userprofiles WHERE user_id = ?1", nativeQuery = true)
    Optional<UserProfiles> findUserProfileByUserId(Long userId);

    @Modifying
    @Query(value = "DELETE FROM userprofiles where user_id = ?1", nativeQuery = true)
    void deleteUserProfileByUserId(Long userId);

}
