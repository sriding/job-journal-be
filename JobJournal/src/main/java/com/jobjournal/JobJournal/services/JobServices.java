package com.jobjournal.JobJournal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobjournal.JobJournal.repositories.JobRepository;

@Service
public class JobServices {
    private final JobRepository jobRepository;

    @Autowired
    public JobServices(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public JobRepository getRepository() {
        return this.jobRepository;
    }
}
