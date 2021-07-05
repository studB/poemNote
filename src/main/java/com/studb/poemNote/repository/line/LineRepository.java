package com.studb.poemNote.repository.line;

import java.sql.Timestamp;
import java.util.List;

import com.studb.poemNote.domain.line.Line;

public interface LineRepository {
    
    List<Line> findAll();

    List<Line> findAny(String textId);

    void save(Line line, Timestamp timestamp);

    int delete(String textId);
    
}
