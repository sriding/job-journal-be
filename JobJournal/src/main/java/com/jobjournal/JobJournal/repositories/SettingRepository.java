package com.jobjournal.JobJournal.repositories;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.jobjournal.JobJournal.shared.models.entity.Setting;

@Transactional
public interface SettingRepository extends JpaRepository<Setting, Long> {
    @Query(value = "SELECT * FROM setting WHERE _user_id_fk_setting = ?1", nativeQuery = true)
    public Optional<Setting> findSettingByUserId(Long _user_id);

    @Modifying
    @Query(value = "DELETE FROM setting where _user_id_fk_setting = ?1", nativeQuery = true)
    public void deleteSettingByUserId(Long _user_id);
}
