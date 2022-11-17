package com.jobjournal.JobJournal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobjournal.JobJournal.repositories.SettingRepository;

@Service
public class SettingServices {
    private SettingRepository settingRepository;

    @Autowired
    public SettingServices(SettingRepository settingRepository) {
        this.settingRepository = settingRepository;
    }

    public SettingRepository getRepository() {
        return this.settingRepository;
    }
}
