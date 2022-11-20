package com.jobjournal.JobJournal.repositories;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jobjournal.JobJournal.shared.models.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value = "SELECT * FROM post WHERE user_id = ?1 ORDER BY post_id DESC LIMIT ?2, ?3", nativeQuery = true)
    public ArrayList<Post> getPosts(Long userId, int startingIndex, int endingIndex);

    @Query(value = "SELECT user_id FROM post WHERE post_id = ?1", nativeQuery = true)
    public Optional<Long> getUserIdFromPostId(Long postId);
}
