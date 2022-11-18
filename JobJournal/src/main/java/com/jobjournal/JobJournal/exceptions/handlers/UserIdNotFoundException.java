package com.jobjournal.JobJournal.exceptions.handlers;

public class UserIdNotFoundException extends RuntimeException {
    public UserIdNotFoundException() {
        super("User ID cannot be found.");
    }
}
