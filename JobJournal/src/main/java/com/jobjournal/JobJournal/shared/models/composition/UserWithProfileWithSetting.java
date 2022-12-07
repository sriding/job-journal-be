package com.jobjournal.JobJournal.shared.models.composition;

import javax.validation.Valid;

import com.jobjournal.JobJournal.shared.interfaces.UserWithProfileWithSettingsInterface;
import com.jobjournal.JobJournal.shared.models.entity.Setting;
import com.jobjournal.JobJournal.shared.models.entity.UserProfiles;
import com.jobjournal.JobJournal.shared.models.entity.Users;

public class UserWithProfileWithSetting implements UserWithProfileWithSettingsInterface {
    @Valid
    private Users _user;
    @Valid
    private UserProfiles _userProfiles;
    @Valid
    private Setting _setting;

    public UserWithProfileWithSetting() {
        this._user = new Users();
        this._userProfiles = new UserProfiles();
        this._setting = new Setting();
    }

    public UserWithProfileWithSetting(Users _user, UserProfiles _userProfiles, Setting _setting) {
        this._user = _user;
        this._userProfiles = _userProfiles;
        this._setting = _setting;
    }

    public Users get_user() {
        return _user;
    }

    public void set_user(Users _user) {
        this._user = _user;
    }

    @Override
    public boolean get_deactivate() {
        return this._user.get_deactivate();
    }

    public UserProfiles get_userProfiles() {
        return _userProfiles;
    }

    public void set_userProfiles(UserProfiles _userProfiles) {
        this._userProfiles = _userProfiles;
    }

    public Setting get_setting() {
        return _setting;
    }

    public void set_setting(Setting _setting) {
        this._setting = _setting;
    }

    @Override
    public Long get_user_id() {
        return this._user.get_user_id();
    }

    @Override
    public String get_auth0_id() {
        return this._user.get_auth0_id();
    }

    @Override
    public Long get_profile_id() {
        return this._userProfiles.get_profile_id();
    }

    @Override
    public String get_profile_name() {
        return this._userProfiles.get_profile_name();
    }

    @Override
    public Long get_user_id_fk_profile() {
        return this._userProfiles.get_user().get_user_id();
    }

    @Override
    public Long get_setting_id() {
        return this._setting.get_setting_id();
    }

    @Override
    public Long get_user_id_fk_setting() {
        return this._setting.get_user().get_user_id();
    }
}
