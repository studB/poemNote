package com.studb.poemNote.repository.theory;

import java.sql.Timestamp;
import java.util.List;

import com.studb.poemNote.domain.theory.Theory;
import com.studb.poemNote.domain.theory.TheoryTitleDto;

public interface TheoryRepository {
    
    List<Theory> findAll();

    List<Theory> findAny(String textId);

    List<TheoryTitleDto> findTheoryTitle();

    void save(Theory theory, Timestamp timestamp);

    int delete(String textId);

    int update(Theory theory, Timestamp timestamp);
    
}
