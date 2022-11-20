package com.jobjournal.JobJournal.shared.models.entity;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.jobjournal.JobJournal.shared.enums.JobStatus;
import com.jobjournal.JobJournal.shared.enums.JobType;

@Entity
@Table(name = "JOB")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "post_id", nullable = false, unique = true)
    private Post post;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "information")
    private String information;

    @Column(name = "location")
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private JobType typeEnum;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private JobStatus statusEnum;

    @Column(name = "application_submitted_date", columnDefinition = "DATE")
    private LocalDate applicationDate;

    @Column(name = "application_dismissed_date", columnDefinition = "DATE")
    private LocalDate applicationDismissedDate;

    @CreationTimestamp
    @Column(name = "creation_date", updatable = false)
    private Date dateCreated;

    @UpdateTimestamp
    @Column(name = "update_date")
    private Date dateUpdated;

    // no-arg constructor required for entity object
    public Job() {
    }

    // If this constructor is used, a post field must be set manually after
    // intialization
    public Job(String title, String information, String location, String type, String status,
            LocalDate applicationDate, LocalDate applicationDismissedDate) throws Exception {
        this.title = title;
        this.information = information;
        this.location = location;
        this.typeEnum = JobType.valueOf(type);
        this.statusEnum = JobStatus.valueOf(status);
        this.applicationDate = applicationDate;
        this.applicationDismissedDate = applicationDismissedDate;
    }

    public Job(Post post, String title, String information, String location, String type, String status,
            LocalDate applicationDate, LocalDate applicationDismissedDate) throws Exception {
        this.post = post;
        this.title = title;
        this.information = information;
        this.location = location;
        this.typeEnum = JobType.valueOf(type);
        this.statusEnum = JobStatus.valueOf(status);
        this.applicationDate = applicationDate;
        this.applicationDismissedDate = applicationDismissedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public JobType getTypeEnum() {
        return typeEnum;
    }

    public void setTypeEnum(JobType typeEnum) {
        this.typeEnum = typeEnum;
    }

    public JobStatus getStatusEnum() {
        return statusEnum;
    }

    public void setStatusEnum(JobStatus statusEnum) {
        this.statusEnum = statusEnum;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }

    public LocalDate getApplicationDismissedDate() {
        return applicationDismissedDate;
    }

    public void setApplicationDismissedDate(LocalDate applicationDismissedDate) {
        this.applicationDismissedDate = applicationDismissedDate;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }
}
