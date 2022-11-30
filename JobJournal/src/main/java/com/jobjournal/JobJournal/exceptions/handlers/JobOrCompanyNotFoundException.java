package com.jobjournal.JobJournal.exceptions.handlers;

public class JobOrCompanyNotFoundException extends RuntimeException {
    public JobOrCompanyNotFoundException() {
        super("The job or company cannot be found in the db.");
    }
}
