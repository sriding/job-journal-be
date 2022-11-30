package com.jobjournal.JobJournal.controllers.rest;

import java.util.ArrayList;
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
import com.jobjournal.JobJournal.exceptions.handlers.JobOrCompanyNotFoundException;
import com.jobjournal.JobJournal.exceptions.handlers.PostNotFoundException;
import com.jobjournal.JobJournal.exceptions.handlers.UserIdDoesNotMatchException;
import com.jobjournal.JobJournal.exceptions.handlers.UserIdNotFoundException;
import com.jobjournal.JobJournal.exceptions.handlers.UserNotFoundException;
import com.jobjournal.JobJournal.repositories.CompanyRepository;
import com.jobjournal.JobJournal.repositories.JobRepository;
import com.jobjournal.JobJournal.repositories.PostRepository;
import com.jobjournal.JobJournal.repositories.UsersRepository;
import com.jobjournal.JobJournal.services.CompanyServices;
import com.jobjournal.JobJournal.services.DBTransactionServices;
import com.jobjournal.JobJournal.services.JobServices;
import com.jobjournal.JobJournal.services.PostServices;
import com.jobjournal.JobJournal.services.UsersServices;
import com.jobjournal.JobJournal.shared.datastructures.ResponsePayloadHashMap;
import com.jobjournal.JobJournal.shared.interfaces.PostsWithCompaniesAndJobsInterface;
import com.jobjournal.JobJournal.shared.models.composition.PostsWithCompaniesAndJobs;
import com.jobjournal.JobJournal.shared.models.entity.Company;
import com.jobjournal.JobJournal.shared.models.entity.Job;
import com.jobjournal.JobJournal.shared.models.entity.Post;
import com.jobjournal.JobJournal.shared.models.entity.Users;

@RestController
@RequestMapping(path = "/api/post")
@CrossOrigin
public class PostController extends RequiredAbstractClassForControllers {
    // services
    private final PostServices postServices;
    private final UsersServices usersServices;
    private final DBTransactionServices dbTransactionServices;
    private final CompanyServices companyServices;
    private final JobServices jobServices;

    // constants
    // How many posts should one get request pull at a time? (save memory)
    private final int GET_POSTS_LIMIT = 20;

    // Add in repositories for services
    @Autowired
    public PostController(PostRepository postRepository, UsersRepository usersRepository,
            CompanyRepository companyRepository, JobRepository jobRepository) {
        this.postServices = new PostServices(postRepository);
        this.usersServices = new UsersServices(usersRepository);
        this.dbTransactionServices = new DBTransactionServices(postRepository, jobRepository, companyRepository);
        this.companyServices = new CompanyServices(companyRepository);
        this.jobServices = new JobServices(jobRepository);
    }

    // Gets range of the latest posts for a certain user, by using the token. Limit
    // number decides starting point of range.
    @GetMapping(path = "/get/posts/by/token/{indexLimit}")
    public ResponseEntity<?> getPostsByToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @PathVariable int indexLimit) {
        try {
            Optional<Long> userId = getUserIdByToken(token, getAuth0Domain(), this.usersServices.getRepository());
            if (userId.isPresent()) {
                ArrayList<Post> postArray = this.postServices.getRepository().getPosts(userId.get(), indexLimit,
                        indexLimit + GET_POSTS_LIMIT);
                return ResponseEntity.ok()
                        .body(new ResponsePayloadHashMap(true, "", postArray).getResponsePayloadHashMap());

            } else {
                throw new UserIdNotFoundException();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ResponsePayloadHashMap(false, e.getMessage(), null).getResponsePayloadHashMap());
        }
    }

    @GetMapping(path = "/get/posts/with/company/and/job/by/token")
    public ResponseEntity<?> getPostsWithCompaniesWithJobsByToken(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            Optional<Long> userId = getUserIdByToken(token, getAuth0Domain(), this.usersServices.getRepository());
            if (userId.isPresent()) {
                ArrayList<PostsWithCompaniesAndJobsInterface> pCJArrayList = this.postServices.getRepository()
                        .getPostsWithCompaniesWithJobsNoStartingIndex(userId.get());
                return ResponseEntity.ok()
                        .body(new ResponsePayloadHashMap(true, "", pCJArrayList).getResponsePayloadHashMap());
            } else {
                throw new UserIdNotFoundException();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ResponsePayloadHashMap(false, e.getMessage(), null).getResponsePayloadHashMap());
        }
    }

    @GetMapping(path = "/get/posts/with/company/and/job/by/token/{postId}")
    public ResponseEntity<?> getPostsWithCompaniesWithJobsByTokenWithStartingIndex(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Long postId) {
        try {
            Optional<Long> userId = getUserIdByToken(token, getAuth0Domain(), this.usersServices.getRepository());
            if (userId.isPresent()) {
                ArrayList<PostsWithCompaniesAndJobsInterface> pCJArrayList = this.postServices.getRepository()
                        .getPostsWithCompaniesWithJobsWithStartingIndex(userId.get(), postId);
                return ResponseEntity.ok()
                        .body(new ResponsePayloadHashMap(true, "", pCJArrayList).getResponsePayloadHashMap());
            } else {
                throw new UserIdNotFoundException();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ResponsePayloadHashMap(false, e.getMessage(), null).getResponsePayloadHashMap());
        }
    }

    // TODO: More GET methods to account for filtering

    // Create a post using token and post object from request body
    @PostMapping(path = "/create/post/by/token")
    public ResponseEntity<?> createPostByToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @RequestBody Post post) {
        try {
            System.out.println(post);
            Optional<Users> user = getUserByToken(token, getAuth0Domain(), this.usersServices.getRepository());
            if (user.isPresent()) {
                // Post in request body does not have User attribute set, so it must be manually
                // set
                post.set_user(user.get());
                Post newPost = this.postServices.getRepository()
                        .save(this.postServices.getRepository().save(post));
                return ResponseEntity.ok()
                        .body(new ResponsePayloadHashMap(true, "", newPost).getResponsePayloadHashMap());
            } else {
                throw new UserNotFoundException();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ResponsePayloadHashMap(false, e.getMessage(), null).getResponsePayloadHashMap());
        }
    }

    @PostMapping(path = "/create/post/with/company/with/job/by/token")
    public ResponseEntity<?> createPostWithCompanyWithJobByToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @RequestBody PostsWithCompaniesAndJobs pcj) {
        try {
            Optional<Users> user = getUserByToken(token, getAuth0Domain(), this.usersServices.getRepository());
            if (user.isPresent()) {
                Post newPost = new Post(user.get(), pcj.get_post().get_post_notes());
                PostsWithCompaniesAndJobs postsWithCompaniesAndJobs = this.dbTransactionServices
                        .createPostWithCompanyWithJob(newPost,
                                new Company(newPost, pcj.get_company().get_company_name(),
                                        pcj.get_company().get_company_website(),
                                        pcj.get_company().get_company_information()),
                                new Job(newPost, pcj.get_job().get_job_title(), pcj.get_job().get_job_information(),
                                        pcj.get_job().get_job_location(),
                                        pcj.get_job().get_job_type(), pcj.get_job().get_job_status(),
                                        pcj.get_job().get_job_application_submitted_date(),
                                        pcj.get_job().get_job_application_dismissed_date()));
                return ResponseEntity.ok().body(
                        new ResponsePayloadHashMap(true, "", postsWithCompaniesAndJobs).getResponsePayloadHashMap());

            } else {
                throw new UserNotFoundException();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ResponsePayloadHashMap(false, e.getMessage(), null).getResponsePayloadHashMap());
        }
    }

    // Update a post using a token and post id, with request body containing a post
    // object
    @PutMapping(path = "/update/post/by/{postId}")
    public ResponseEntity<?> updatePostByPostId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @PathVariable Long postId,
            @RequestBody Post post) {
        try {
            Optional<Long> userId = getUserIdByToken(token, getAuth0Domain(), this.usersServices.getRepository());
            if (userId.isPresent()) {
                Optional<Post> dbPost = this.postServices.getRepository().findById(postId);
                if (dbPost.isPresent()) {
                    if (userId.get() == dbPost.get().get_user().get_user_id()) {
                        dbPost.map(p -> {
                            p.set_post_notes(post.get_post_notes());

                            return this.postServices.getRepository().save(p);
                        });

                        return ResponseEntity.ok()
                                .body(new ResponsePayloadHashMap(true, "", dbPost).getResponsePayloadHashMap());
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

    @DeleteMapping(path = "/delete/post/by/{postId}")
    public ResponseEntity<?> deletePostByPostId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @PathVariable Long postId) {
        try {
            Optional<Long> userId = getUserIdByToken(token, getAuth0Domain(), this.usersServices.getRepository());
            if (userId.isPresent()) {
                Optional<Long> storedUserIdInPost = this.postServices.getRepository().getUserIdFromPostId(postId);
                if (storedUserIdInPost.isPresent()) {
                    if (userId.get() == storedUserIdInPost.get()) {
                        Optional<Long> companyId = this.companyServices.getRepository().findCompanyIdByPostId(postId);
                        Optional<Long> jobId = this.jobServices.getRepository().getJobIdByPostId(postId);
                        if (companyId.isPresent() && jobId.isPresent()) {
                            this.dbTransactionServices.deletePostWithCompanyWithJob(postId, companyId.get(),
                                    jobId.get());
                            return ResponseEntity.ok()
                                    .body(new ResponsePayloadHashMap(true,
                                            "Post, company, and job successfully deleted", null)
                                            .getResponsePayloadHashMap());
                        } else {
                            throw new JobOrCompanyNotFoundException();
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
}
