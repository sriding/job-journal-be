package com.jobjournal.JobJournal.services;

import org.springframework.stereotype.Service;

import com.jobjournal.JobJournal.repositories.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UsersServices {
    private UsersRepository usersRepository;

    @Autowired
    public UsersServices(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public UsersRepository getRepository() {
        return this.usersRepository;
    }
}
