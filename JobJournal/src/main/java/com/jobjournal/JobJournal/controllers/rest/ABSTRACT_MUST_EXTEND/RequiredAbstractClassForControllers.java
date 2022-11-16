package com.jobjournal.JobJournal.controllers.rest.ABSTRACT_MUST_EXTEND;

import java.util.Optional;

import com.jobjournal.JobJournal.exceptions.handlers.UserNotFoundException;
import com.jobjournal.JobJournal.repositories.UsersRepository;
import com.jobjournal.JobJournal.shared.helpers.Auth0RequestService;

public abstract class RequiredAbstractClassForControllers {
    public Optional<Long> getUserIdByToken(String token, String auth0Domain, UsersRepository usersRepository)
            throws Exception {
        String auth0_id = Auth0RequestService.getSubFromUserInfoInAuth0(token, auth0Domain);
        Optional<Long> userId = usersRepository.findUserIdByAuth0Id(auth0_id);
        if (userId.isPresent()) {
            return userId;
        } else {
            throw new UserNotFoundException();
        }
    }

    public String getAuth0IdByToken(String token, String auth0Domain)
            throws Exception {
        return Auth0RequestService.getSubFromUserInfoInAuth0(token, auth0Domain);
    }
}
