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
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "USERS")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "auth0_id", unique = true, nullable = false)
    private String auth0Id;

    @CreationTimestamp
    @Column(name = "creation_date", updatable = false)
    private Date dateCreated;

    @UpdateTimestamp
    @Column(name = "update_date")
    private Date dateUpdated;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Setting setting;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserProfiles profile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Post> posts;

    // no-arg constructor required for entity object
    public Users() {
    }

    public Users(String auth0Id) {
        this.auth0Id = auth0Id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuth0Id() {
        return auth0Id;
    }

    public void setAuth0Id(String auth0Id) {
        this.auth0Id = auth0Id;
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
