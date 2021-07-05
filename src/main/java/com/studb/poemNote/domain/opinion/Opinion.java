package com.studb.poemNote.domain.opinion;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.format.annotation.DateTimeFormat;

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
public class Opinion {
    
    private Long id;
    private String textId;
    private String body;
    private String tag;
    private Timestamp createdAt;
    private Timestamp modifiedAt;

}
