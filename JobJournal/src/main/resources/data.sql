INSERT INTO users (_user_id, _auth0_id, _deactivate) VALUES (1, "google-oauth2|110428753866664923333", false);
INSERT INTO userprofiles (_profile_id, _profile_name, _user_id_fk_profile) VALUES (1, "Stephen Riding", 1);
INSERT INTO setting (_setting_id, _user_id_fk_setting) VALUES (1, 1);
INSERT INTO post (_post_id, _post_notes, _user_id_fk_post) VALUES (1, "testing post notes", 1);
INSERT INTO company (_company_id, _company_name, _company_information, _company_website, _post_id_fk_company) VALUES (1, "testing company name", "", "", 1);
INSERT INTO job (_job_id, _job_title, _job_type, _job_information, _job_location, _job_status, _job_application_submitted_date, _job_application_dismissed_date, _post_id_fk_job) VALUES (1, "testing job title", "", "", "", "", "", "", 1);