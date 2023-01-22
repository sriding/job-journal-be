package com.jobjournal.JobJournal.controllers.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jobjournal.JobJournal.controllers.rest.ABSTRACT_MUST_EXTEND.RequiredAbstractClassForControllers;
import com.jobjournal.JobJournal.exceptions.handlers.UserIdNotFoundException;
import com.jobjournal.JobJournal.exceptions.handlers.UserNotFoundException;
import com.jobjournal.JobJournal.exceptions.handlers.UserProfileNotFoundException;
import com.jobjournal.JobJournal.repositories.UserProfilesRepository;
import com.jobjournal.JobJournal.repositories.UsersRepository;
import com.jobjournal.JobJournal.services.UserProfilesServices;
import com.jobjournal.JobJournal.services.UsersServices;
import com.jobjournal.JobJournal.shared.datastructures.ResponsePayloadHashMap;
import com.jobjournal.JobJournal.shared.helpers.Auth0RequestService;
import com.jobjournal.JobJournal.shared.models.entity.UserProfiles;
import com.jobjournal.JobJournal.shared.models.entity.Users;

@RestController
@RequestMapping(path = "/api/userprofiles")
@Validated
@CrossOrigin(origins = { "${spring.frontend.url}" }, maxAge = 3600)
public class UserProfilesController extends RequiredAbstractClassForControllers {
    // Services
    private final UserProfilesServices userProfilesServices;
    private final UsersServices usersServices;

    // Add in repositories to work with services
    @Autowired
    public UserProfilesController(UserProfilesRepository userProfilesRepository, UsersRepository usersRepository) {
        this.userProfilesServices = new UserProfilesServices(userProfilesRepository);
        this.usersServices = new UsersServices(usersRepository);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ResponsePayloadHashMap tempHM = new ResponsePayloadHashMap();
        tempHM.set_success(false);
        tempHM.set_payload(null);
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            tempHM.getResponsePayloadHashMap().put("_message", errorMessage);
        });

        return ResponseEntity.badRequest().body(tempHM.getResponsePayloadHashMap());
    }

    // Get profile through the use of the Auth0 token
    @GetMapping(path = "/get/userprofile/by/token")
    public ResponseEntity<?> getUserProfileByUserId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            Optional<Long> userId = getUserIdByToken(token, getAuth0Domain(), this.usersServices.getRepository());
            if (userId.isPresent()) {
                Optional<UserProfiles> userProfiles = this.userProfilesServices.getRepository()
                        .findUserProfileByUserId(userId.get());
                if (userProfiles.isPresent()) {
                    return ResponseEntity.ok()
                            .body(new ResponsePayloadHashMap(true, "", userProfiles).getResponsePayloadHashMap());

                } else {
                    throw new UserProfileNotFoundException();
                }
            } else {
                throw new UserIdNotFoundException();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ResponsePayloadHashMap(false, e.getMessage(), null).getResponsePayloadHashMap());
        }
    }

    // TODO: Handle if the userprofile already exists; should still return success.
    // Create profile through the use of Auth0 token
    @PostMapping(path = "/create/userprofile/by/token")
    public ResponseEntity<?> createUserProfileByUserId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            Optional<Users> user = getUserByToken(token, getAuth0Domain(), this.usersServices.getRepository());
            if (user.isPresent()) {
                String name = Auth0RequestService.getFullNameFromUserInfoInAuth0(token, getAuth0Domain());
                UserProfiles userProfiles = this.userProfilesServices.getRepository()
                        .save(new UserProfiles(user.get(), name));
                return ResponseEntity.ok()
                        .body(new ResponsePayloadHashMap(true, "", userProfiles).getResponsePayloadHashMap());
            } else {
                throw new UserNotFoundException();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ResponsePayloadHashMap(false, e.getMessage(), null).getResponsePayloadHashMap());
        }
    }

    // TODO: Add functionality to create a temporary new profile just to be deleted.
    // For testing purposes.
    // Delete profile through use of the Auth0 token
    @DeleteMapping(path = "/delete/userprofile/by/token")
    public ResponseEntity<?> deleteUserProfileByUserId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            Optional<Long> userId = getUserIdByToken(token, getAuth0Domain(), this.usersServices.getRepository());
            if (userId.isPresent()) {
                int rowsDeleted = this.userProfilesServices.getRepository().deleteUserProfileByUserId(userId.get());
                if (rowsDeleted >= 1) {
                    return ResponseEntity.ok().body(new ResponsePayloadHashMap(true, String.valueOf(rowsDeleted), null)
                            .getResponsePayloadHashMap());
                } else {
                    throw new UserProfileNotFoundException();
                }
            } else {
                throw new UserIdNotFoundException();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ResponsePayloadHashMap(false, e.getMessage(), null).getResponsePayloadHashMap());
        }
    }

    // TODO: Updating User Profiles
}
