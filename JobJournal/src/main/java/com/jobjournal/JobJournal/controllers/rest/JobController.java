package com.jobjournal.JobJournal.controllers.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobjournal.JobJournal.controllers.rest.ABSTRACT_MUST_EXTEND.RequiredAbstractClassForControllers;
import com.jobjournal.JobJournal.exceptions.handlers.JobNotFoundException;
import com.jobjournal.JobJournal.exceptions.handlers.PostNotFoundException;
import com.jobjournal.JobJournal.exceptions.handlers.UserIdDoesNotMatchException;
import com.jobjournal.JobJournal.exceptions.handlers.UserIdNotFoundException;
import com.jobjournal.JobJournal.repositories.JobRepository;
import com.jobjournal.JobJournal.repositories.PostRepository;
import com.jobjournal.JobJournal.repositories.UsersRepository;
import com.jobjournal.JobJournal.services.JobServices;
import com.jobjournal.JobJournal.services.PostServices;
import com.jobjournal.JobJournal.services.UsersServices;
import com.jobjournal.JobJournal.shared.datastructures.ResponsePayloadHashMap;
import com.jobjournal.JobJournal.shared.models.entity.Job;
import com.jobjournal.JobJournal.shared.models.entity.Post;

@RestController
@RequestMapping(path = "/api/job")
@CrossOrigin
public class JobController extends RequiredAbstractClassForControllers {
    // Services
    private final JobServices jobServices;
    private final PostServices postServices;
    private final UsersServices usersServices;

    // Add in repositories to be used by services
    @Autowired
    public JobController(JobRepository jobRepository, PostRepository postRepository, UsersRepository usersRepository) {
        this.jobServices = new JobServices(jobRepository);
        this.postServices = new PostServices(postRepository);
        this.usersServices = new UsersServices(usersRepository);
    }

    // Get job data by token and post id
    @GetMapping(path = "/get/job/by/{postId}")
    public ResponseEntity<?> getJobByPostId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @PathVariable Long postId) {
        try {
            // Have to confirm the user id obtained through the token matches the user id
            // present in the post obtained from the postId
            Optional<Long> userId = getUserIdByToken(token, getAuth0Domain(), this.usersServices.getRepository());
            if (userId.isPresent()) {
                Optional<Post> post = this.postServices.getRepository().findById(postId);
                if (post.isPresent()) {
                    if (userId.get() == post.get().get_user().get_user_id()) {
                        Optional<Job> job = this.jobServices.getRepository().getJobByPostId(postId);
                        if (job.isPresent()) {
                            return ResponseEntity.ok()
                                    .body(new ResponsePayloadHashMap(true, "", job).getResponsePayloadHashMap());

                        } else {
                            throw new JobNotFoundException();
                        }
                    } else {
                        throw new UserIdDoesNotMatchException();
                    }
                } else {
                    throw new PostNotFoundException();
                }
            } else {
                throw new UserIdNotFoundException();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ResponsePayloadHashMap(false, e.getMessage(), null).getResponsePayloadHashMap());
        }
    }

    // Create job using token and post id with job object in the body of the request
    @PostMapping(path = "/create/job/by/{postId}")
    public ResponseEntity<?> createJobByPostId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @RequestBody Job job, @PathVariable Long postId) {
        try {
            // Have to confirm the user id obtained through the token matches the user id
            // present in the post obtained from the postId
            Optional<Long> userId = getUserIdByToken(token, getAuth0Domain(), this.usersServices.getRepository());
            if (userId.isPresent()) {
                Optional<Post> post = this.postServices.getRepository().findById(postId);
                if (post.isPresent()) {
                    if (userId.get() == post.get().get_user().get_user_id()) {
                        // The client does not include the Post attribute in the request body, so it
                        // must be manually added
                        job.set_post(post.get());
                        Job newJob = this.jobServices.getRepository().save(job);
                        return ResponseEntity.ok()
                                .body(new ResponsePayloadHashMap(true, "", newJob).getResponsePayloadHashMap());
                    } else {
                        throw new UserIdDoesNotMatchException();
                    }
                } else {
                    throw new PostNotFoundException();
                }
            } else {
                throw new UserIdNotFoundException();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ResponsePayloadHashMap(false, e.getMessage(), null).getResponsePayloadHashMap());
        }
    }

    // Update job using token and post id with job object in the body of the request
    @PutMapping(path = "/update/job/by/{postId}")
    public ResponseEntity<?> updateJobByPostId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @RequestBody Job job, @PathVariable Long postId) {
        try {
            // Have to confirm the user id obtained through the token matches the user id
            // present in the post obtained from the postId
            Optional<Long> userId = getUserIdByToken(token, getAuth0Domain(), this.usersServices.getRepository());
            if (userId.isPresent()) {
                Optional<Post> post = this.postServices.getRepository().findById(postId);
                if (post.isPresent()) {
                    if (userId.get() == post.get().get_user().get_user_id()) {
                        Optional<Job> dbJob = this.jobServices.getRepository().getJobByPostId(postId);
                        dbJob.map(j -> {
                            j.set_job_title(job.get_job_title());
                            j.set_job_information(job.get_job_information());
                            j.set_job_location(job.get_job_location());
                            j.set_job_type(job.get_job_type());
                            j.set_job_status(job.get_job_status());
                            j.set_job_application_submitted_date(job.get_job_application_submitted_date());
                            j.set_job_application_dismissed_date(job.get_job_application_dismissed_date());

                            return this.jobServices.getRepository().save(j);
                        });

                        return ResponseEntity.ok()
                                .body(new ResponsePayloadHashMap(true, "", dbJob).getResponsePayloadHashMap());
                    } else {
                        throw new UserIdDoesNotMatchException();
                    }
                } else {
                    throw new PostNotFoundException();
                }
            } else {
                throw new UserIdNotFoundException();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ResponsePayloadHashMap(false, e.getMessage(), null).getResponsePayloadHashMap());
        }
    }

    // Delete job using token and postId
    @DeleteMapping(path = "/delete/job/by/{postId}")
    public ResponseEntity<?> deleteJobByPostId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @PathVariable Long postId) {
        try {
            // Have to confirm the user id obtained through the token matches the user id
            // present in the post obtained from the postId
            Optional<Long> userId = getUserIdByToken(token, getAuth0Domain(), this.usersServices.getRepository());
            if (userId.isPresent()) {
                Optional<Post> post = this.postServices.getRepository().findById(postId);
                if (post.isPresent()) {
                    if (userId.get() == post.get().get_user().get_user_id()) {
                        this.jobServices.getRepository().deleteJobByPostId(postId);
                        return ResponseEntity.ok()
                                .body(new ResponsePayloadHashMap(true, "Job successfully deleted.", null)
                                        .getResponsePayloadHashMap());
                    } else {
                        throw new UserIdDoesNotMatchException();
                    }
                } else {
                    throw new PostNotFoundException();
                }
            } else {
                throw new UserIdNotFoundException();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ResponsePayloadHashMap(false, e.getMessage(), null).getResponsePayloadHashMap());
        }
    }
}
