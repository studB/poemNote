package com.studb.poemNote.repository.opinion;

import java.sql.Timestamp;
import java.util.List;

import com.studb.poemNote.domain.opinion.Opinion;
import com.studb.poemNote.domain.opinion.OpinionTitleDto;

public interface OpinionRepository {
    
    List<Opinion> findAll();

    List<Opinion> findAny(String textId);

    List<OpinionTitleDto> findOpinionTitle();

    void save(Opinion opinion, Timestamp timestamp);

    int delete(String textId);

    int update(Opinion opinion, Timestamp timestamp);
    
}
