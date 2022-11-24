package com.jobjournal.JobJournal.shared.models.entity;

import java.sql.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long _user_id;

    @Column(unique = true, nullable = false)
    private String _auth0_id;

    @CreationTimestamp
    @Column(updatable = false)
    private Date _creation_date;

    @UpdateTimestamp
    private Date _update_date;

    @OneToOne(mappedBy = "_user", cascade = CascadeType.ALL)
    private Setting _setting;

    @OneToOne(mappedBy = "_user", cascade = CascadeType.ALL)
    private UserProfiles _profile;

    @OneToMany(mappedBy = "_user", cascade = CascadeType.ALL)
    private Set<Post> _posts;

    // no-arg constructor required for entity object
    public Users() {
    }

    public Users(String _auth0_id) {
        this._auth0_id = _auth0_id;
    }

    public Long get_user_id() {
        return _user_id;
    }

    public void set_user_id(Long _user_id) {
        this._user_id = _user_id;
    }

    public String get_auth0_id() {
        return _auth0_id;
    }

    public void set_auth0_id(String _auth0_id) {
        this._auth0_id = _auth0_id;
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
