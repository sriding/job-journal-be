package com.jobjournal.JobJournal.shared.interfaces;

public interface UserWithProfileWithSettingsInterface {
    Long get_user_id();

    boolean get_deactivate();

    String get_auth0_id();

    Long get_profile_id();

    String get_profile_name();

    Long get_user_id_fk_profile();

    Long get_setting_id();

    Long get_user_id_fk_setting();
}
