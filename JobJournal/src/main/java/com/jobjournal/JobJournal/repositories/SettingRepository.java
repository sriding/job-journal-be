package com.jobjournal.JobJournal.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jobjournal.JobJournal.shared.models.entity.Setting;

public interface SettingRepository extends JpaRepository<Setting, Long> {
    @Query(value = "SELECT * FROM setting WHERE user_id = ?1", nativeQuery = true)
    public Optional<Setting> findSettingByUserId(Long userId);

    @Query(value = "DELETE FROM setting where user_id = ?1")
    public void deleteSettingByUserId(Long userId);
}
