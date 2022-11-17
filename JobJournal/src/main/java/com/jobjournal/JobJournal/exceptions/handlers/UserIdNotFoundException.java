package com.jobjournal.JobJournal.exceptions.handlers;

public class UserIdNotFoundException extends RuntimeException {
    public UserIdNotFoundException() {
        super("Could not find this User Id in the database.");
    }
}
