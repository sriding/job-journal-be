package com.jobjournal.JobJournal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobjournal.JobJournal.shared.models.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {

}
