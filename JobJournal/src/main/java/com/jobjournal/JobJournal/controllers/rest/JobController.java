package com.jobjournal.JobJournal.controllers.rest;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import com.jobjournal.JobJournal.shared.enums.JobStatus;
import com.jobjournal.JobJournal.shared.enums.JobType;
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

    @Autowired
    public JobController(JobRepository jobRepository, PostRepository postRepository, UsersRepository usersRepository) {
        this.jobServices = new JobServices(jobRepository);
        this.postServices = new PostServices(postRepository);
        this.usersServices = new UsersServices(usersRepository);
    }

    @GetMapping(path = "/getjobbypostid")
    public ResponseEntity<?> getJobByPostId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, Long postId) {
        try {
            // Have to confirm the user id obtained from using the token matches the user id
            // obtained from the post in our database
            Optional<Long> userId = getUserIdByToken(token, getAuth0Domain(), this.usersServices.getRepository());
            if (userId.isPresent()) {
                Optional<Post> post = this.postServices.getRepository().findById(postId);
                if (post.isPresent()) {
                    if (userId.get() == post.get().getUser().getId()) {
                        return ResponseEntity.ok().body(this.jobServices.getRepository().getJobByPostId(postId));
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

    @PostMapping(path = "/create")
    public ResponseEntity<?> createJobByPostId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, Long postId,
            String title, String information, String location, String type, String status, LocalDate applicationDate,
            LocalDate applicationDismissedDate) {
        try {
            // Have to confirm the user id obtained from using the token matches the user id
            // obtained from the post in our database
            Optional<Long> userId = getUserIdByToken(token, getAuth0Domain(), this.usersServices.getRepository());
            if (userId.isPresent()) {
                Optional<Post> post = this.postServices.getRepository().findById(postId);
                if (post.isPresent()) {
                    if (userId.get() == post.get().getUser().getId()) {
                        Job newJob = new Job(postId, title, information, location, type, status, applicationDate,
                                applicationDismissedDate);
                        return ResponseEntity.ok().body(this.jobServices.getRepository().save(newJob));
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

    @PutMapping(path = "/update")
    public ResponseEntity<?> updateJobByPostId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, Long postId,
            String title, String information, String location, String type, String status, LocalDate applicationDate,
            LocalDate applicationDismissedDate) {
        try {
            // Have to confirm the user id obtained from using the token matches the user id
            // obtained from the post in our database
            Optional<Long> userId = getUserIdByToken(token, getAuth0Domain(), this.usersServices.getRepository());
            if (userId.isPresent()) {
                Optional<Post> post = this.postServices.getRepository().findById(postId);
                if (post.isPresent()) {
                    if (userId.get() == post.get().getUser().getId()) {
                        Optional<Job> job = this.jobServices.getRepository().getJobByPostId(postId);
                        if (job.isPresent()) {
                            job.map(j -> {
                                j.setTitle(title);
                                j.setInformation(information);
                                j.setLocation(location);
                                j.setTypeEnum(JobType.valueOf(type));
                                j.setStatusEnum(JobStatus.valueOf(status));
                                j.setApplicationDate(applicationDate);
                                j.setApplictionDismissedDate(applicationDismissedDate);
                                return this.jobServices.getRepository().save(j);
                            });

                            return ResponseEntity.ok().body(job);
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
            return ResponseEntity.badRequest().body(e);
        }
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<?> deleteJobByPostId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, Long postId) {
        try {
            // Have to confirm the user id obtained from using the token matches the user id
            // obtained from the post in our database
            Optional<Long> userId = getUserIdByToken(token, getAuth0Domain(), this.usersServices.getRepository());
            if (userId.isPresent()) {
                Optional<Post> post = this.postServices.getRepository().findById(postId);
                if (post.isPresent()) {
                    if (userId.get() == post.get().getUser().getId()) {
                        this.jobServices.getRepository().deleteJobByPostId(postId);
                        return ResponseEntity.ok().body(null);
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
