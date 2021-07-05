package com.studb.poemNote.domain.line;

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
public class Line {
    
    private Long id;
    private String textId;
    private String body;
    private Timestamp createdAt;
    
}
