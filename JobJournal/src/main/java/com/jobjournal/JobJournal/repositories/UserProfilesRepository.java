package com.jobjournal.JobJournal.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jobjournal.JobJournal.shared.interfaces.repositories.SearchForUidInterface;
import com.jobjournal.JobJournal.shared.models.entity.UserProfiles;

public interface UserProfilesRepository extends JpaRepository<UserProfiles, Long>, SearchForUidInterface<UserProfiles> {
    @Query(value = "SELECT * FROM PROFILE WHERE auth0_uid = ?1", nativeQuery = true)
    Optional<UserProfiles> findByUid(String uid);
}
