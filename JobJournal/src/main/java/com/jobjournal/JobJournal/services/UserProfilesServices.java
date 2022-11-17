package com.jobjournal.JobJournal.services;

import com.jobjournal.JobJournal.repositories.UserProfilesRepository;

public class UserProfilesServices {
    private UserProfilesRepository userProfilesRepository;

    public UserProfilesServices(UserProfilesRepository userProfilesRepository) {
        this.userProfilesRepository = userProfilesRepository;
    }

    public UserProfilesRepository getRepository() {
        return this.userProfilesRepository;
    }
}
