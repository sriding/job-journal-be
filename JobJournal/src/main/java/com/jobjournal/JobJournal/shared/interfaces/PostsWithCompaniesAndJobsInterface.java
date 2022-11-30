package com.jobjournal.JobJournal.shared.interfaces;

public interface PostsWithCompaniesAndJobsInterface {
    Long get_post_id();

    String get_post_notes();

    Long get_user_id_fk_post();

    Long get_company_id();

    String get_company_information();

    String get_company_name();

    String get_company_website();

    Long get_post_id_fk_company();

    Long get_job_id();

    String get_job_application_dismissed_date();

    String get_job_application_submitted_date();

    String get_job_information();

    String get_job_location();

    String get_job_status();

    String get_job_title();

    String get_job_type();

    Long get_post_id_fk_job();
}
