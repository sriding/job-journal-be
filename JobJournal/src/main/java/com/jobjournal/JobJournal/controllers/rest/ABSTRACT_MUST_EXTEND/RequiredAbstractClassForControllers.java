package com.jobjournal.JobJournal.controllers.rest.ABSTRACT_MUST_EXTEND;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;

import com.jobjournal.JobJournal.exceptions.handlers.UserIdNotFoundException;
import com.jobjournal.JobJournal.exceptions.handlers.UserNotFoundException;
import com.jobjournal.JobJournal.repositories.UsersRepository;
import com.jobjournal.JobJournal.shared.helpers.Auth0RequestService;
import com.jobjournal.JobJournal.shared.models.entity.Users;

public abstract class RequiredAbstractClassForControllers {
    // Domain for communicating with Auth0
    @Value("${AUTH0_DOMAIN}")
    private String auth0Domain;
    @Value("${AUTH0_AUDIENCE}")
    private String auth0Audience;

    protected final Optional<Long> getUserIdByToken(String token, String auth0Domain, UsersRepository usersRepository)
            throws Exception {
        String auth0_id = Auth0RequestService.getSubFromUserInfoInAuth0(token, auth0Domain);
        Optional<Long> userId = usersRepository.findUserIdByAuth0Id(auth0_id);
        if (userId.isPresent()) {
            return userId;
        } else {
            throw new UserIdNotFoundException();
        }
    }

    protected final Optional<Users> getUserByToken(String token, String auth0Domain, UsersRepository usersRepository)
            throws Exception {
        String auth0_id = Auth0RequestService.getSubFromUserInfoInAuth0(token, auth0Domain);
        Optional<Users> user = usersRepository.findUserByAuth0Id(auth0_id);
        if (user.isPresent()) {
            return user;
        } else {
            throw new UserNotFoundException();
        }
    }

    protected final String getAuth0IdByToken(String token, String auth0Domain)
            throws Exception {
        return Auth0RequestService.getSubFromUserInfoInAuth0(token, auth0Domain);
    }

    protected final String getAuth0Domain() {
        return this.auth0Domain;
    }

    protected final String getAuth0Audience() {
        return this.auth0Audience;
    }
}
