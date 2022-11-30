package com.jobjournal.JobJournal.exceptions.handlers;

public class SettingNotFoundException extends RuntimeException {
    public SettingNotFoundException() {
        super("Setting not found in database.");
    }
}
