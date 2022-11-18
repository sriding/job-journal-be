package com.jobjournal.JobJournal.exceptions.handlers;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException() {
        super("Post cannot be found.");
    }
}
