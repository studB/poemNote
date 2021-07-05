package com.studb.poemNote.repository.poem;

import java.sql.Timestamp;
import java.util.List;

import com.studb.poemNote.domain.poem.Poem;
import com.studb.poemNote.domain.poem.PoemNoPublishedDto;
import com.studb.poemNote.domain.poem.PoemTitleDto;

public interface PoemRepository {
    
    List<Poem> findAll();

    List<Poem> findAny(String textId);

    List<PoemTitleDto> findPoemTitle();

    List<PoemNoPublishedDto> findAllNoPublished();

    List<Poem> findAllPublished(String textId);

    void save(Poem poem, Timestamp timestamp);

    int delete(String textId);

    int update(Poem Poem, Timestamp timestamp);

    void publish(Poem poem, Timestamp timestamp);
    
    int updatePublished(Poem poem, Timestamp timestamp);

}
