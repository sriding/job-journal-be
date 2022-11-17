package com.jobjournal.JobJournal.exceptions.handlers;

public class Auth0IdNotFoundException extends RuntimeException {
    public Auth0IdNotFoundException() {
        super("Could not find this Auth0 Id in the database.");
    }
}
