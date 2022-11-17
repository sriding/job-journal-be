package com.jobjournal.JobJournal.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jobjournal.JobJournal.shared.models.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value = "SELECT * FROM post WHERE user_id = ?1 ORDER BY post_id DESC LIMIT 10;", nativeQuery = true)
    public ArrayList<Post> getPosts(Long userId);
}
