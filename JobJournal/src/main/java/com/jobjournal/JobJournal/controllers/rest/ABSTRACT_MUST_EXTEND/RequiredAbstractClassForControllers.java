package com.jobjournal.JobJournal.controllers.rest.ABSTRACT_MUST_EXTEND;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;

import com.jobjournal.JobJournal.exceptions.handlers.Auth0IdNotFoundException;
import com.jobjournal.JobJournal.repositories.UsersRepository;
import com.jobjournal.JobJournal.shared.helpers.Auth0RequestService;

public abstract class RequiredAbstractClassForControllers {
    // Domain for communicating with Auth0
    @Value("${AUTH0_DOMAIN}")
    private String auth0Domain;

    public final <T> Optional<Long> getUserIdByToken(String token, String auth0Domain, UsersRepository repository)
            throws Exception {
        String auth0_id = Auth0RequestService.getSubFromUserInfoInAuth0(token, auth0Domain);
        Optional<Long> userId = repository.findUserIdByAuth0Id(auth0_id);
        if (userId.isPresent()) {
            return userId;
        } else {
            throw new Auth0IdNotFoundException();
        }
    }

    public final String getAuth0IdByToken(String token, String auth0Domain)
            throws Exception {
        return Auth0RequestService.getSubFromUserInfoInAuth0(token, auth0Domain);
    }

    public final String getAuth0Domain() {
        return this.auth0Domain;
    }
}
