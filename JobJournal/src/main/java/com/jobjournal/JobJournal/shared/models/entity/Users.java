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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long _user_id;

    @NotBlank
    @Pattern(regexp = "^[^&<>`]*$", message = "Text cannot contain the following characters: >^<`&")
    @Column(unique = true, nullable = false)
    private String _auth0_id;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(updatable = false)
    private Date _user_creation_date;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date _user_update_date;

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

    public Date get_user_creation_date() {
        return _user_creation_date;
    }

    public void set_user_creation_date(Date _user_creation_date) {
        this._user_creation_date = _user_creation_date;
    }

    public Date get_user_update_date() {
        return _user_update_date;
    }

    public void set_user_update_date(Date _user_update_date) {
        this._user_update_date = _user_update_date;
    }
}
