package com.jobjournal.JobJournal.controllers.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
import com.jobjournal.JobJournal.exceptions.handlers.PostNotFoundException;
import com.jobjournal.JobJournal.exceptions.handlers.UserIdDoesNotMatchException;
import com.jobjournal.JobJournal.exceptions.handlers.UserIdNotFoundException;
import com.jobjournal.JobJournal.repositories.JobRepository;
import com.jobjournal.JobJournal.repositories.PostRepository;
import com.jobjournal.JobJournal.repositories.UsersRepository;
import com.jobjournal.JobJournal.services.JobServices;
import com.jobjournal.JobJournal.services.PostServices;
import com.jobjournal.JobJournal.services.UsersServices;
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
                    if (userId.get() == post.get().getUser().getId()) {
                        return ResponseEntity.ok()
                                .body(this.jobServices.getRepository().getJobByPostId(postId));
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
            return ResponseEntity.badRequest().body(e);
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
                    if (userId.get() == post.get().getUser().getId()) {
                        // The client does not include the Post attribute in the request body, so it
                        // must be manually added
                        job.setPost(post.get());
                        return ResponseEntity.ok().body(this.jobServices.getRepository().save(job));
                    } else {
                        throw new UserIdDoesNotMatchException();
                    }
                } else {
                    throw new PostNotFoundException();
                }
            } else {
                throw new UserIdNotFoundException();
            }
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.badRequest().body("Null value is invalidating request.");
        } catch (OptimisticLockingFailureException olfe) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(olfe);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
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
                    if (userId.get() == post.get().getUser().getId()) {
                        Optional<Job> dbJob = this.jobServices.getRepository().getJobByPostId(postId);
                        dbJob.map(j -> {
                            j.setTitle(job.getTitle());
                            j.setInformation(job.getInformation());
                            j.setLocation(job.getLocation());
                            j.setTypeEnum(job.getTypeEnum());
                            j.setStatusEnum(job.getStatusEnum());
                            j.setApplicationDate(job.getApplicationDate());
                            j.setApplicationDismissedDate(job.getApplicationDismissedDate());

                            return this.jobServices.getRepository().save(j);
                        });

                        return ResponseEntity.ok().body(job);
                    } else {
                        throw new UserIdDoesNotMatchException();
                    }
                } else {
                    throw new PostNotFoundException();
                }
            } else {
                throw new UserIdNotFoundException();
            }
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.badRequest().body("Null value is invalidating request.");
        } catch (OptimisticLockingFailureException olfe) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(olfe);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
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
                    if (userId.get() == post.get().getUser().getId()) {
                        this.jobServices.getRepository().deleteJobByPostId(postId);
                        return ResponseEntity.ok().body("Deleted.");
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
            return ResponseEntity.badRequest().body(e);
        }
    }
}
