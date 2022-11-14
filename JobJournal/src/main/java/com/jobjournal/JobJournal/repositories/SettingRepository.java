package com.jobjournal.JobJournal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobjournal.JobJournal.shared.models.entity.Setting;

public interface SettingRepository extends JpaRepository<Setting, Long> {

}
