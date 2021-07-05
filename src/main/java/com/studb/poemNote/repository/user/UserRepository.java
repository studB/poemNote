package com.studb.poemNote.repository.user;

import java.util.List;
import java.util.Optional;

import com.studb.poemNote.domain.user.User;

public interface UserRepository {
    
    List<User> findAll();

    Optional<User> check(String key);

}
