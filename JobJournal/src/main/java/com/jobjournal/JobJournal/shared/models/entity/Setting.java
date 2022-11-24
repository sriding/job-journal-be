package com.jobjournal.JobJournal.shared.models.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class Setting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long _setting_id;

    @OneToOne
    @JoinColumn(name = "_user_id", nullable = false, unique = true)
    private Users _user;

    @CreationTimestamp
    @Column(updatable = false)
    private Date _creation_date;

    @UpdateTimestamp
    private Date _update_date;

    // no-arg constructor required for entity object
    public Setting() {
    }

    public Setting(Users user) {
        this._user = user;
    }

    public Long get_setting_id() {
        return _setting_id;
    }

    public void set_setting_id(Long _setting_id) {
        this._setting_id = _setting_id;
    }

    public Users get_user() {
        return _user;
    }

    public void set_user(Users _user) {
        this._user = _user;
    }

    public Date get_creation_date() {
        return _creation_date;
    }

    public void set_creation_date(Date _creation_date) {
        this._creation_date = _creation_date;
    }

    public Date get_update_date() {
        return _update_date;
    }

    public void set_update_date(Date _update_date) {
        this._update_date = _update_date;
    }
}
