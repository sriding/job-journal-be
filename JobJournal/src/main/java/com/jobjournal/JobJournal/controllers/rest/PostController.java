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
import com.jobjournal.JobJournal.exceptions.handlers.UserNotFoundException;
import com.jobjournal.JobJournal.repositories.PostRepository;
import com.jobjournal.JobJournal.repositories.UsersRepository;
import com.jobjournal.JobJournal.services.PostServices;
import com.jobjournal.JobJournal.services.UsersServices;
import com.jobjournal.JobJournal.shared.models.entity.Post;
import com.jobjournal.JobJournal.shared.models.entity.Users;

@RestController
@RequestMapping(path = "/api/post")
@CrossOrigin
public class PostController extends RequiredAbstractClassForControllers {
    // services
    private final PostServices postServices;
    private final UsersServices usersServices;

    // constants
    // How many posts should one get request pull at a time? (save memory)
    private final int GET_POSTS_LIMIT = 20;

    // Add in repositories for services
    @Autowired
    public PostController(PostRepository postRepository, UsersRepository usersRepository) {
        this.postServices = new PostServices(postRepository);
        this.usersServices = new UsersServices(usersRepository);
    }

    // Gets range of the latest posts for a certain user, by using the token. Limit
    // number decides starting point of range.
    @GetMapping(path = "/get/posts/by/token/{indexLimit}")
    public ResponseEntity<?> getPostsByToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @PathVariable int indexLimit) {
        try {
            Optional<Long> userId = getUserIdByToken(token, getAuth0Domain(), this.usersServices.getRepository());
            if (userId.isPresent()) {
                return ResponseEntity.ok()
                        .body(this.postServices.getRepository().getPosts(userId.get(), indexLimit,
                                indexLimit + GET_POSTS_LIMIT));
            } else {
                throw new UserIdNotFoundException();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    // TODO: More GET methods to account for filtering

    // Create a post using token and post object from request body
    @PostMapping(path = "/create/post/by/token")
    public ResponseEntity<?> createPostByToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @RequestBody Post post) {
        try {
            Optional<Users> user = getUserByToken(token, getAuth0Domain(), this.usersServices.getRepository());
            if (user.isPresent()) {
                // Post in request body does not have User attribute set, so it must be manually
                // set
                post.setUser(user.get());
                return ResponseEntity.ok().body(this.postServices.getRepository()
                        .save(this.postServices.getRepository().save(post)));
            } else {
                throw new UserNotFoundException();
            }
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.badRequest().body("Null value is invalidating request.");
        } catch (OptimisticLockingFailureException olfe) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(olfe);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
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
                    if (userId.get() == dbPost.get().getUser().getId()) {
                        dbPost.map(p -> {
                            p.setNotes(post.getNotes());

                            return this.postServices.getRepository().save(p);
                        });

                        return ResponseEntity.ok().body(post);
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

    // TODO: Requires multiple steps and the use of multiple services
    @DeleteMapping(path = "/delete/post/by/{postId}")
    public ResponseEntity<?> deletePostByPostId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @PathVariable Long postId,
            @RequestBody Post post) {
        try {
            // Save company in memory
            // Attempt to delete company from db, if error return error
            // Save job in memory
            // Attempt to delete job from db, if error restore company to db and return
            // error
            // Attempt to delete post from db, if error restore job and company to db and
            // return error
            return ResponseEntity.ok().body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }
}
