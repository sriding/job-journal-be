package com.jobjournal.JobJournal.exceptions.handlers;

public class JobNotFoundException extends RuntimeException {
    public JobNotFoundException() {
        super("Job cannot be found.");
    }
}
