package com.studb.poemNote.domain.theory;

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
public class Theory {

    private Long id;
    private String textId;
    private String title;
    private String body;
    private String tag;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    
}
