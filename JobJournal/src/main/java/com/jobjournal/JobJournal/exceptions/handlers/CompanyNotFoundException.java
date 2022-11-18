package com.jobjournal.JobJournal.exceptions.handlers;

public class CompanyNotFoundException extends RuntimeException {
    public CompanyNotFoundException() {
        super("Company cannot be found.");
    }
}
