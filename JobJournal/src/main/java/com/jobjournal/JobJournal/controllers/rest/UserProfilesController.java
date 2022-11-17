package com.jobjournal.JobJournal.controllers.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import com.jobjournal.JobJournal.repositories.UserProfilesRepository;
import com.jobjournal.JobJournal.repositories.UsersRepository;
import com.jobjournal.JobJournal.services.UserProfilesServices;
import com.jobjournal.JobJournal.services.UsersServices;
import com.jobjournal.JobJournal.shared.helpers.Auth0RequestService;
import com.jobjournal.JobJournal.shared.models.entity.UserProfiles;

@RestController
@RequestMapping(path = "/api/userprofiles")
@CrossOrigin
public class UserProfilesController extends RequiredAbstractClassForControllers {
    // Services
    private final UserProfilesServices userProfilesServices;
    private final UsersServices usersServices;

    @Autowired
    public UserProfilesController(UserProfilesRepository userProfilesRepository, UsersRepository usersRepository) {
        this.userProfilesServices = new UserProfilesServices(userProfilesRepository);
        this.usersServices = new UsersServices(usersRepository);
    }

    @GetMapping(path = "/getbyuserid")
    public ResponseEntity<?> getUserProfileByUserId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            Optional<Long> userId = getUserIdByToken(token, getAuth0Domain(), this.usersServices.getRepository());
            if (userId.isPresent()) {
                return ResponseEntity.ok()
                        .body(this.userProfilesServices.getRepository().findUserProfileByUserId(userId.get()));
            } else {
                throw new UserIdNotFoundException();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping(path = "/create")
    public ResponseEntity<?> createUserProfileByUserId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            Optional<Long> userId = getUserIdByToken(token, getAuth0Domain(), this.usersServices.getRepository());
            if (userId.isPresent()) {
                String name = Auth0RequestService.getFullNameFromUserInfoInAuth0(token, getAuth0Domain());
                return ResponseEntity.ok().body(new UserProfiles(userId.get(), name));
            } else {
                throw new UserIdNotFoundException();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<?> deleteUserProfileByUserId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            Optional<Long> userId = getUserIdByToken(token, getAuth0Domain(), this.usersServices.getRepository());
            if (userId.isPresent()) {
                this.userProfilesServices.getRepository().deleteUserProfileByUserId(userId.get());
                return ResponseEntity.ok().body(null);
            } else {
                throw new UserIdNotFoundException();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    // TODO: Updating User Profiles
}
