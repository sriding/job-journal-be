package com.jobjournal.JobJournal.shared.models.entity;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long _post_id;

    @ManyToOne
    @JoinColumn(name = "_user_id", nullable = false)
    private Users _user;

    private String _post_notes;

    @CreationTimestamp
    @Column(updatable = false)
    private Date _creation_date;

    @UpdateTimestamp
    private Date _update_date;

    @OneToOne(mappedBy = "_post", cascade = CascadeType.ALL)
    private Company _company;

    @OneToOne(mappedBy = "_post", cascade = CascadeType.ALL)
    private Job _job;

    // no-arg constructor required for entity object
    public Post() {
    }

    // If this constructor is used, the user field must be set manually after
    // initalization
    public Post(String _post_notes) {
        this._post_notes = _post_notes;
    }

    public Post(Users _user, String _post_notes) {
        this._user = _user;
        this._post_notes = _post_notes;
    }

    public Long get_post_id() {
        return _post_id;
    }

    public void set_post_id(Long _post_id) {
        this._post_id = _post_id;
    }

    public Users get_user() {
        return _user;
    }

    public void set_user(Users _user) {
        this._user = _user;
    }

    public String get_post_notes() {
        return _post_notes;
    }

    public void set_post_notes(String _post_notes) {
        this._post_notes = _post_notes;
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
