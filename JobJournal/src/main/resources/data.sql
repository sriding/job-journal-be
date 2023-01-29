INSERT INTO users (_user_id, _auth0_id, _deactivate)
SELECT 1, "google-oauth2|110428753866664923333", false
WHERE NOT EXISTS
    (SELECT _user_id FROM users where _auth0_id = "google-oauth2|110428753866664923333");
INSERT INTO userprofiles (_profile_id, _profile_name, _user_id_fk_profile) 
SELECT 1, "Stephen Riding", 1
WHERE NOT EXISTS
    (SELECT _profile_id FROM userprofiles WHERE _profile_name = "Stephen Riding");
INSERT INTO setting (_setting_id, _user_id_fk_setting)
SELECT 1,1
WHERE NOT EXISTS
    (SELECT _setting_id FROM setting WHERE _user_id_fk_setting = 1);
INSERT INTO post (_post_id, _post_notes, _user_id_fk_post)
SELECT 1, "testing post notes", 1
WHERE NOT EXISTS
    (SELECT _post_id FROM post WHERE _post_notes = "testing post notes");
INSERT INTO company (_company_id, _company_name, _company_information, _company_website, _post_id_fk_company)
SELECT 1, "testing company name", "", "", 1
WHERE NOT EXISTS
    (SELECT _company_id FROM company WHERE _company_name = "testing company name");
INSERT INTO job (_job_id, _job_title, _job_type, _job_information, _job_location, _job_status, _job_application_submitted_date, _job_application_dismissed_date, _post_id_fk_job)
SELECT 1, "testing job title", "", "", "", "", "", "", 1
WHERE NOT EXISTS
    (SELECT _job_id FROM job WHERE _job_title = "testing job title");