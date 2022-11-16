package com.jobjournal.JobJournal.controllers.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobjournal.JobJournal.controllers.rest.ABSTRACT_MUST_EXTEND.RequiredAbstractClassForControllers;
import com.jobjournal.JobJournal.exceptions.handlers.UserNotFoundException;
import com.jobjournal.JobJournal.repositories.UsersRepository;
import com.jobjournal.JobJournal.services.UsersServices;
import com.jobjournal.JobJournal.shared.models.entity.Users;

@RestController
@RequestMapping(path = "/api/users")
@CrossOrigin
public class UsersController extends RequiredAbstractClassForControllers {
    // Domain for auth0
    @Value("${AUTH0_DOMAIN}")
    private String auth0Domain;

    // Services
    private UsersServices usersServices;

    public UsersController(UsersServices usersServices, UsersRepository usersRepository) {
        this.usersServices = new UsersServices(usersRepository);
    }

    @GetMapping(path = "/getbyid")
    public ResponseEntity<?> getUserId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            Optional<Long> id = getUserIdByToken(token, auth0Domain, this.usersServices.getRepository());
            return ResponseEntity.ok().body(id);
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body("Firebase Uid cannot be null.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Server is having issues, try again later or report steps that led to this occuring.");
        }
    }

    @PostMapping(path = "/create")
    public ResponseEntity<?> createUid(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            String auth0id = getAuth0IdByToken(token, auth0Domain);
            Users newUser = new Users(auth0id);
            return ResponseEntity.ok().body(this.usersServices.getRepository().save(newUser));
        } catch (IllegalArgumentException ie) {
            return ResponseEntity.badRequest().body("Firebase Uid cannot be null.");
        } catch (OptimisticLockingFailureException ofe) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Database is having issues (Version attribute is different or firebase table not found).");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Server is having issues, try again later or report steps that led to this occuring.");
        }
    }

    // Will most likely delete this
    @GetMapping(path = "/gettoken")
    public ResponseEntity<?> getCurrentUserAuth0Id(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @Value("${AUTH0_DOMAIN}") String domain) {
        try {
            return ResponseEntity.ok().body(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Server is having issues, try again later or report steps that led to this occuring.");
        }
    }

    /*
     * @PutMapping(path = "/update")
     * public ResponseEntity<?> updateUid(@RequestBody Auth0Uid firebaseUid,
     * Auth0Uid newFirebaseUid) {
     * try {
     * Optional<Auth0Uid> entityFound =
     * this.repository.findByUid(firebaseUid.getId());
     * if (entityFound.isPresent()) {
     * entityFound.get().setId(newFirebaseUid.getId());
     * return ResponseEntity.ok(this.repository.save(entityFound.get()));
     * } else {
     * return ResponseEntity.badRequest().body("Uid does not exist in system.");
     * }
     * } catch (IllegalArgumentException ie) {
     * return ResponseEntity.badRequest().body("Firebase Uid cannot be null.");
     * } catch (OptimisticLockingFailureException ofe) {
     * return ResponseEntity.status(HttpStatus.CONFLICT)
     * .body("Database is having issues (Version attribute is different or firebase table not found)."
     * );
     * } catch (Exception e) {
     * return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
     * .body("Server is having issues, try again later or report steps that led to this occuring."
     * );
     * }
     * }
     */
}
