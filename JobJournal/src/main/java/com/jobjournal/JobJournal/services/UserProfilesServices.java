package com.jobjournal.JobJournal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobjournal.JobJournal.repositories.UserProfilesRepository;

@Service
public class UserProfilesServices {
    private final UserProfilesRepository userProfilesRepository;

    @Autowired
    public UserProfilesServices(UserProfilesRepository userProfilesRepository) {
        this.userProfilesRepository = userProfilesRepository;
    }

    public UserProfilesRepository getRepository() {
        return this.userProfilesRepository;
    }
}
