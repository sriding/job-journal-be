package com.jobjournal.JobJournal.repositories;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.jobjournal.JobJournal.shared.models.entity.Setting;

@Transactional
public interface SettingRepository extends JpaRepository<Setting, Long> {
    @Query(value = "SELECT * FROM setting WHERE user_id = ?1", nativeQuery = true)
    public Optional<Setting> findSettingByUserId(Long userId);

    @Modifying
    @Query(value = "DELETE FROM setting where user_id = ?1", nativeQuery = true)
    public void deleteSettingByUserId(Long userId);
}
