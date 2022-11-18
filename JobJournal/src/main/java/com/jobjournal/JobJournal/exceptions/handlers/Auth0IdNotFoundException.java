package com.jobjournal.JobJournal.exceptions.handlers;

public class Auth0IdNotFoundException extends RuntimeException {
    public Auth0IdNotFoundException() {
        super("Auth0 Id cannot be found.");
    }
}
