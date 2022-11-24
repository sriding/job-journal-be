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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobjournal.JobJournal.controllers.rest.ABSTRACT_MUST_EXTEND.RequiredAbstractClassForControllers;
import com.jobjournal.JobJournal.exceptions.handlers.UserIdNotFoundException;
import com.jobjournal.JobJournal.repositories.UsersRepository;
import com.jobjournal.JobJournal.services.UsersServices;
import com.jobjournal.JobJournal.shared.models.entity.Users;

@RestController
@RequestMapping(path = "/api/users")
@CrossOrigin
public class UsersController extends RequiredAbstractClassForControllers {
    // Services
    private final UsersServices usersServices;

    // Pass in repositories that will work with services
    @Autowired
    public UsersController(UsersRepository usersRepository) {
        this.usersServices = new UsersServices(usersRepository);
    }

    // Get User Id using Auth0 token
    @GetMapping(path = "/get/userid/by/token")
    public ResponseEntity<?> getUserIdUsingToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            Optional<Long> userId = getUserIdByToken(token, getAuth0Domain(), this.usersServices.getRepository());
            if (userId.isPresent()) {
                return ResponseEntity.ok().body(userId);
            } else {
                throw new UserIdNotFoundException();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Create a new user using Auth0 token and requesting Auth0 user id from Auth0
    @PostMapping(path = "/create/newuser/by/token")
    public ResponseEntity<?> createUserUsingToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            String auth0Id = getAuth0IdByToken(token, getAuth0Domain());
            return ResponseEntity.ok().body(this.usersServices.getRepository().save(new Users(auth0Id)));
        } catch (IllegalArgumentException ie) {
            return ResponseEntity.badRequest().body("Auth0 Id cannot be null.");
        } catch (OptimisticLockingFailureException ofe) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ofe.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // TODO: REQUIRES WORKING WITH AUTH0 USER MANAGEMENT TO COMPLETELY
    // REMOVE USER, REQUIRES 3 DIFFERENT SERVICES, REQUIRES MULTIPLE TRY CATCH
    // BLOCKS!
    @DeleteMapping(path = "/delete")
    public ResponseEntity<?> deleteUserUsingToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            // First save setting information in memory for the user.
            // Next attempt to delete setting information from db. If this fails, return
            // error.
            // First save profile information in memory for the user.
            // Next attempt to delete profile information from db. If this fails, add
            // setting information back to db and return error.
            // First save user information in memory for the user.
            // Next attempt to delete user information from db. If this fails, add both
            // profile and setting information back to db and return error.
            return ResponseEntity.ok().body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // For development purposes...
    @GetMapping(path = "/get/token/by/token")
    public ResponseEntity<?> getCurrentUserAuth0Id(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            return ResponseEntity.ok().body(token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
