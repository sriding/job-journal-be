package com.jobjournal.JobJournal.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jobjournal.JobJournal.shared.interfaces.UserWithProfileWithSettingsInterface;
import com.jobjournal.JobJournal.shared.models.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {
    @Query(value = "SELECT _user_id FROM users WHERE _auth0_id = ?1", nativeQuery = true)
    Optional<Long> findUserIdByAuth0Id(String _auth0_id);

    @Query(value = "SELECT * FROM users WHERE _auth0_id = ?1", nativeQuery = true)
    Optional<Users> findUserByAuth0Id(String _auth0_id);

    @Query(value = "SELECT u._user_id, u._deactivate, u._auth0_id, up._profile_id, up._profile_name, up._user_id_fk_profile, s._setting_id, s._user_id_fk_setting FROM users u INNER JOIN userprofiles up ON up._user_id_fk_profile = u._user_id INNER JOIN setting s ON s._user_id_fk_setting = u._user_id WHERE u._user_id = ?1", nativeQuery = true)
    Optional<UserWithProfileWithSettingsInterface> findUserWithProfileWithSettingByUserId(Long _user_id);
}
