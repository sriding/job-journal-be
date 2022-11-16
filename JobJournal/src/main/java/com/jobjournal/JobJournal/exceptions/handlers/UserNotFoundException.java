package com.jobjournal.JobJournal.exceptions.handlers;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User could not be found.");
    }
}
