package com.jobjournal.JobJournal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobjournal.JobJournal.shared.models.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
