package com.jobjournal.JobJournal.shared.models.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long _company_id;

    @Valid
    @OneToOne
    @JoinColumn(name = "_post_id_fk_company", referencedColumnName = "_post_id", nullable = false, unique = true)
    private Post _post;

    @Pattern(regexp = "^[^&<>`]*$", message = "Text cannot contain the following characters: >^<`&")
    @Column(nullable = false)
    private String _company_name;

    @Pattern(regexp = "^[^&<>`]*$", message = "Text cannot contain the following characters: >^<`&")
    private String _company_website;

    @Pattern(regexp = "^[^&<>`]*$", message = "Text cannot contain the following characters: >^<`&")
    @Size(max = 6000, message = "Maximum of 6000 characters.")
    @Column(length = 6000)
    private String _company_information;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(updatable = false)
    private Date _company_creation_date;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date _company_update_date;

    // no-arg constructor required for entity object
    public Company() {
    }

    // If this constructor is used, the post will need to be set manually after
    // intialization
    public Company(String _company_name, String _company_website, String _company_information) {
        this._company_name = _company_name;
        this._company_website = _company_website;
        this._company_information = _company_information;
    }

    public Company(Post _post, String _company_name, String _company_website, String _company_information) {
        this._post = _post;
        this._company_name = _company_name;
        this._company_website = _company_website;
        this._company_information = _company_information;
    }

    public Long get_company_id() {
        return _company_id;
    }

    public void set_company_id(Long _company_id) {
        this._company_id = _company_id;
    }

    public Post get_post() {
        return _post;
    }

    public void set_post(Post _post) {
        this._post = _post;
    }

    public String get_company_name() {
        return _company_name;
    }

    public void set_company_name(String _company_name) {
        this._company_name = _company_name;
    }

    public String get_company_website() {
        return _company_website;
    }

    public void set_company_website(String _company_website) {
        this._company_website = _company_website;
    }

    public String get_company_information() {
        return _company_information;
    }

    public void set_company_information(String _company_information) {
        this._company_information = _company_information;
    }

    public Date get_company_creation_date() {
        return _company_creation_date;
    }

    public void set_company_creation_date(Date _company_creation_date) {
        this._company_creation_date = _company_creation_date;
    }

    public Date get_company_update_date() {
        return _company_update_date;
    }

    public void set_company_update_date(Date _company_update_date) {
        this._company_update_date = _company_update_date;
    }
}
