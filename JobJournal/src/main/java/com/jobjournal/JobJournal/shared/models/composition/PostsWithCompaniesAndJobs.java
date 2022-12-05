package com.jobjournal.JobJournal.shared.models.composition;

import javax.validation.Valid;

import com.jobjournal.JobJournal.shared.models.entity.Company;
import com.jobjournal.JobJournal.shared.models.entity.Job;
import com.jobjournal.JobJournal.shared.models.entity.Post;

public class PostsWithCompaniesAndJobs {
    @Valid
    private Post _post;
    @Valid
    private Company _company;
    @Valid
    private Job _job;

    public PostsWithCompaniesAndJobs(Post _post, Company _company, Job _job) {
        this._post = _post;
        this._company = _company;
        this._job = _job;
    }

    public Post get_post() {
        return _post;
    }

    public void set_post(Post _post) {
        this._post = _post;
    }

    public Company get_company() {
        return _company;
    }

    public void set_company(Company _company) {
        this._company = _company;
    }

    public Job get_job() {
        return _job;
    }

    public void set_job(Job _job) {
        this._job = _job;
    }
}
