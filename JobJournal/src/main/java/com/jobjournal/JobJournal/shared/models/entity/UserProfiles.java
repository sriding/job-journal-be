package com.jobjournal.JobJournal.shared.models.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "userprofiles")
public class UserProfiles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long _profile_id;

    @OneToOne
    @JoinColumn(name = "_user_id_fk_profile", referencedColumnName = "_user_id", nullable = false, unique = true)
    private Users _user;

    @Column(nullable = false)
    private String _profile_name;

    @CreationTimestamp
    @Column(updatable = false)
    private Date _profile_creation_date;

    @UpdateTimestamp
    private Date _profile_update_date;

    // no arg constructor required for entity object
    public UserProfiles() {
    }

    public UserProfiles(Users _user, String _profile_name) {
        this._user = _user;
        this._profile_name = _profile_name;
    }

    public Long get_profile_id() {
        return _profile_id;
    }

    public void set_profile_id(Long _profile_id) {
        this._profile_id = _profile_id;
    }

    public Users get_user() {
        return _user;
    }

    public void set_user(Users _user) {
        this._user = _user;
    }

    public String get_profile_name() {
        return _profile_name;
    }

    public void set_profile_name(String _profile_name) {
        this._profile_name = _profile_name;
    }

    public Date get_profile_creation_date() {
        return _profile_creation_date;
    }

    public void set_profile_creation_date(Date _profile_creation_date) {
        this._profile_creation_date = _profile_creation_date;
    }

    public Date get_profile_update_date() {
        return _profile_update_date;
    }

    public void set_profile_update_date(Date _profile_update_date) {
        this._profile_update_date = _profile_update_date;
    }
}
