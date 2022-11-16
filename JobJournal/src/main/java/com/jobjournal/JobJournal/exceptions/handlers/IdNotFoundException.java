package com.jobjournal.JobJournal.exceptions.handlers;

public class IdNotFoundException extends RuntimeException {
    public IdNotFoundException(Long id) {
        super("Could not find id " + id + " in database.");
    }
}
