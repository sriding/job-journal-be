package com.jobjournal.JobJournal.services;

import com.jobjournal.JobJournal.repositories.PostRepository;

public class PostServices {
    private PostRepository postRepository;

    public PostServices(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostRepository getRepository() {
        return this.postRepository;
    }
}
