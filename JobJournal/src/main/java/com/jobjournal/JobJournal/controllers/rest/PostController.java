package com.jobjournal.JobJournal.controllers.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobjournal.JobJournal.controllers.rest.ABSTRACT_MUST_EXTEND.RequiredAbstractClassForControllers;
import com.jobjournal.JobJournal.exceptions.handlers.PostNotFoundException;
import com.jobjournal.JobJournal.exceptions.handlers.UserIdNotFoundException;
import com.jobjournal.JobJournal.repositories.PostRepository;
import com.jobjournal.JobJournal.repositories.UsersRepository;
import com.jobjournal.JobJournal.services.PostServices;
import com.jobjournal.JobJournal.services.UsersServices;
import com.jobjournal.JobJournal.shared.models.entity.Post;

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
    public ResponseEntity<?> createPostByUserId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @RequestBody String notes) {
        try {
            Optional<Long> userId = getUserIdByToken(token, getAuth0Domain(), this.usersServices.getRepository());
            if (userId.isPresent()) {
                Post newPost = new Post(notes);
                return ResponseEntity.ok().body(this.postServices.getRepository().save(newPost));
            } else {
                throw new UserIdNotFoundException();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PutMapping(path = "/update")
    public ResponseEntity<?> updatePostByPostId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @RequestBody String notes, @RequestBody Long postId) {
        try {
            Optional<Long> userId = getUserIdByToken(token, getAuth0Domain(), this.usersServices.getRepository());
            if (userId.isPresent()) {
                Optional<Post> post = this.postServices.getRepository().findById(postId);
                if (post.isPresent()) {
                    post.map(p -> {
                        p.setNotes(notes);
                        return this.postServices.getRepository().save(p);
                    });

                    return ResponseEntity.ok().body(post);
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

    // TODO: Requires multiple steps and the use of multiple services
    @DeleteMapping(path = "/delete")
    public ResponseEntity<?> deletePostByPostId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @RequestBody Long postId) {
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
