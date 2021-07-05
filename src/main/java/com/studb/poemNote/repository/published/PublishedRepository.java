package com.studb.poemNote.repository.published;

import java.lang.StackWalker.Option;
import java.util.Optional;

public interface PublishedRepository {
    
    void save(String category, String textId);

    int delete(String textId);

    Optional<Integer> up(String textId);

}
