package com.jobjournal.JobJournal.exceptions.handlers;

public class UserProfileOrSettingsNotFoundException extends RuntimeException {
    public UserProfileOrSettingsNotFoundException() {
        super("User Profile or Setting was not found for the user.");
    }
}
