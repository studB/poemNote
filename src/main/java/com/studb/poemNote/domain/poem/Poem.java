package com.studb.poemNote.domain.poem;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class Poem {
    
    private Long id;
    private String textId;
    private Boolean published;
    private int publishIndex;
    private Timestamp publishTime;
    private String title;
    private String body;
    private String tag;
    private String completedStatus;
    private String writtenStatus;
    private String valueStatus;
    private Timestamp createdAt;
    private Timestamp modifiedAt;

}
