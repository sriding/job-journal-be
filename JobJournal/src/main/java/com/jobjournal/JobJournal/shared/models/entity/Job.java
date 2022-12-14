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
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long _job_id;

    @Valid
    @OneToOne
    @JoinColumn(name = "_post_id_fk_job", referencedColumnName = "_post_id", nullable = false, unique = true)
    private Post _post;

    @Pattern(regexp = "^[^&<>`]*$", message = "Text cannot contain the following characters: >^<`&")
    @Column(nullable = false)
    private String _job_title;

    @Pattern(regexp = "^[^&<>`]*$", message = "Text cannot contain the following characters: >^<`&")
    @Size(max = 6000, message = "Maximum of 6000 characters.")
    @Column(length = 6000)
    private String _job_information;

    @Pattern(regexp = "^[^&<>`]*$", message = "Text cannot contain the following characters: >^<`&")
    private String _job_location;

    @Pattern(regexp = "^[^&<>`]*$", message = "Text cannot contain the following characters: >^<`&")
    private String _job_type;

    @Pattern(regexp = "^[^&<>`]*$", message = "Text cannot contain the following characters: >^<`&")
    private String _job_status;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Pattern(regexp = "^[0-9-]*$", message = "Text can only consist of numbers and dashes")
    private String _job_application_submitted_date;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Pattern(regexp = "^[0-9-]*$", message = "Text can only consist of numbers and dashes")
    private String _job_application_dismissed_date;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(updatable = false)
    private Date _job_creation_date;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @UpdateTimestamp
    private Date _job_update_date;

    // no-arg constructor required for entity object
    public Job() {
    }

    // If this constructor is used, a post field must be set manually after
    // intialization
    public Job(String _job_title, String _job_information, String _job_location, String _job_type, String _job_status,
            String _job_application_submitted_date, String _job_application_dismissed_date) throws Exception {
        this._job_title = _job_title;
        this._job_information = _job_information;
        this._job_location = _job_location;
        this._job_type = _job_type;
        this._job_status = _job_status;
        this._job_application_submitted_date = _job_application_submitted_date;
        this._job_application_dismissed_date = _job_application_dismissed_date;
    }

    public Job(Post _post, String _job_title, String _job_information, String _job_location, String _job_type,
            String _job_status,
            String _job_application_submitted_date, String _job_application_dismissed_date) throws Exception {
        this._post = _post;
        this._job_title = _job_title;
        this._job_information = _job_information;
        this._job_location = _job_location;
        this._job_type = _job_type;
        this._job_status = _job_status;
        this._job_application_submitted_date = _job_application_submitted_date;
        this._job_application_dismissed_date = _job_application_dismissed_date;
    }

    public Long get_job_id() {
        return _job_id;
    }

    public void set_job_id(Long _job_id) {
        this._job_id = _job_id;
    }

    public Post get_post() {
        return _post;
    }

    public void set_post(Post _post) {
        this._post = _post;
    }

    public String get_job_title() {
        return _job_title;
    }

    public void set_job_title(String _job_title) {
        this._job_title = _job_title;
    }

    public String get_job_information() {
        return _job_information;
    }

    public void set_job_information(String _job_information) {
        this._job_information = _job_information;
    }

    public String get_job_location() {
        return _job_location;
    }

    public void set_job_location(String _job_location) {
        this._job_location = _job_location;
    }

    public String get_job_type() {
        return _job_type;
    }

    public void set_job_type(String _job_type) {
        this._job_type = _job_type;
    }

    public String get_job_status() {
        return _job_status;
    }

    public void set_job_status(String _job_status) {
        this._job_status = _job_status;
    }

    public String get_job_application_submitted_date() {
        return _job_application_submitted_date;
    }

    public void set_job_application_submitted_date(String _job_application_submitted_date) {
        this._job_application_submitted_date = _job_application_submitted_date;
    }

    public String get_job_application_dismissed_date() {
        return _job_application_dismissed_date;
    }

    public void set_job_application_dismissed_date(String _job_application_dismissed_date) {
        this._job_application_dismissed_date = _job_application_dismissed_date;
    }

    public Date get_job_creation_date() {
        return _job_creation_date;
    }

    public void set_job_creation_date(Date _job_creation_date) {
        this._job_creation_date = _job_creation_date;
    }

    public Date get_job_update_date() {
        return _job_update_date;
    }

    public void set_job_update_date(Date _job_update_date) {
        this._job_update_date = _job_update_date;
    }
}
