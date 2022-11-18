package com.jobjournal.JobJournal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobjournal.JobJournal.repositories.PostRepository;

@Service
public class PostServices {
    private final PostRepository postRepository;

    @Autowired
    public PostServices(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostRepository getRepository() {
        return this.postRepository;
    }
}
