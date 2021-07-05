package com.studb.poemNote.repository.log;

import java.util.List;

import com.studb.poemNote.domain.log.Log;

public interface LogRepository {
    
    List<Log> findAll();

    void insertLog(Log log);
    
}
