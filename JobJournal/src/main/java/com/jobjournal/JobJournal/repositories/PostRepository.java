package com.jobjournal.JobJournal.repositories;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jobjournal.JobJournal.shared.interfaces.PostsWithCompaniesAndJobsInterface;
import com.jobjournal.JobJournal.shared.models.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value = "SELECT * FROM post WHERE _user_id_fk_post = ?1 ORDER BY _post_id DESC LIMIT ?2, ?3", nativeQuery = true)
    ArrayList<Post> getPosts(Long _user_id, int startingIndex, int endingIndex);

    @Query(value = "SELECT _user_id_fk_post FROM post WHERE _post_id = ?1", nativeQuery = true)
    Optional<Long> getUserIdFromPostId(Long _post_id);

    @Query(value = "SELECT _post_id, _post_notes, _user_id_fk_post, _company_id, _company_information, _company_name, _company_website, _post_id_fk_company, _job_id, _job_application_dismissed_date, _job_application_submitted_date, _job_information, _job_location, _job_status, _job_title, _job_type, _post_id_fk_job FROM post INNER JOIN company ON company._post_id_fk_company = post._post_id INNER JOIN job ON job._post_id_fk_job = post._post_id WHERE post._user_id_fk_post = ?1 ORDER BY post._post_id DESC LIMIT 20", nativeQuery = true)
    ArrayList<PostsWithCompaniesAndJobsInterface> getPostsWithCompaniesWithJobsNoStartingIndex(Long _user_id);

    @Query(value = "SELECT _post_id, _post_notes, _user_id_fk_post, _company_id, _company_information, _company_name, _company_website, _post_id_fk_company, _job_id, _job_application_dismissed_date, _job_application_submitted_date, _job_information, _job_location, _job_status, _job_title, _job_type, _post_id_fk_job FROM post INNER JOIN company ON company._post_id_fk_company = post._post_id INNER JOIN job ON job._post_id_fk_job = post._post_id WHERE post._user_id_fk_post = ?1 AND post._post_id < ?2 ORDER BY post._post_id DESC LIMIT 20", nativeQuery = true)
    ArrayList<PostsWithCompaniesAndJobsInterface> getPostsWithCompaniesWithJobsWithStartingIndex(Long _user_id,
            Long _post_id);
}
