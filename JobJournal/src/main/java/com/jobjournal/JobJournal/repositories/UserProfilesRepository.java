package com.jobjournal.JobJournal.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jobjournal.JobJournal.shared.models.entity.UserProfiles;

public interface UserProfilesRepository extends JpaRepository<UserProfiles, Long> {
    @Query(value = "SELECT * FROM USERPROFILES WHERE user_id = ?1", nativeQuery = true)
    Optional<UserProfiles> findUserProfileByUserId(Long userId);

    @Query(value = "DELETE FROM USERPROFILES WHERE user_id = ?1", nativeQuery = true)
    void deleteUserProfileByUserId(Long userId);
}
