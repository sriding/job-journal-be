package com.jobjournal.JobJournal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobjournal.JobJournal.repositories.CompanyRepository;

@Service
public class CompanyServices {
    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyServices(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public CompanyRepository getRepository() {
        return this.companyRepository;
    }
}
