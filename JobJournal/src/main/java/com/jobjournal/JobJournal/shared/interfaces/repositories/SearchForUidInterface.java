package com.jobjournal.JobJournal.shared.interfaces.repositories;

import java.util.Optional;

//To be implemented with any repository that has a auth0_uid
public interface SearchForUidInterface<T> {
    Optional<T> findByUid(String uid);
}
