package com.jobjournal.JobJournal.exceptions.handlers;

public class UserProfileNotFoundException extends RuntimeException {
    public UserProfileNotFoundException() {
        super("User profile not found.");
    }
}
