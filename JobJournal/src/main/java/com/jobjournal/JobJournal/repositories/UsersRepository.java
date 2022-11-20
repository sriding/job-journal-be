package com.jobjournal.JobJournal.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jobjournal.JobJournal.shared.models.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {
    @Query(value = "SELECT user_id FROM users WHERE auth0_id = ?1", nativeQuery = true)
    Optional<Long> findUserIdByAuth0Id(String auth0_id);

    @Query(value = "SELECT * FROM users WHERE auth0_id = ?1", nativeQuery = true)
    Optional<Users> findUserByAuth0Id(String auth0_id);
}
