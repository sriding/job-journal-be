package com.jobjournal.JobJournal.exceptions.handlers;

public class UserIdDoesNotMatchException extends RuntimeException {
    public UserIdDoesNotMatchException() {
        super("Internally, the User ID does not match the required resource. This should not be happening.");
    }
}
