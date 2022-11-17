package com.jobjournal.JobJournal.controllers.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobjournal.JobJournal.controllers.rest.ABSTRACT_MUST_EXTEND.RequiredAbstractClassForControllers;
import com.jobjournal.JobJournal.exceptions.handlers.UserIdNotFoundException;
import com.jobjournal.JobJournal.repositories.PostRepository;
import com.jobjournal.JobJournal.repositories.UsersRepository;
import com.jobjournal.JobJournal.services.PostServices;
import com.jobjournal.JobJournal.services.UsersServices;

@RestController
@RequestMapping(path = "/api/post")
@CrossOrigin
public class PostController extends RequiredAbstractClassForControllers {
    // services
    private final PostServices postServices;
    private final UsersServices usersServices;

    // constants
    private final int GET_POSTS_LIMIT = 20;

    @Autowired
    public PostController(PostRepository postRepository, UsersRepository usersRepository) {
        this.postServices = new PostServices(postRepository);
        this.usersServices = new UsersServices(usersRepository);
    }

    // TODO: Incorporate multiplier
    @GetMapping(path = "/getposts")
    public ResponseEntity<?> getPostsByUserId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            int postsMultiplier) {
        try {
            Optional<Long> userId = getUserIdByToken(token, getAuth0Domain(), this.usersServices.getRepository());
            if (userId.isPresent()) {
                return ResponseEntity.ok().body(this.postServices.getRepository().getPosts(userId.get()));
            } else {
                throw new UserIdNotFoundException();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    // TODO: More GET methods to account for filtering

    @PostMapping(path = "/create")
    public ResponseEntity<?> createPostByUserId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

    }
}
