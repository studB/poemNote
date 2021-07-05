package com.studb.poemNote.domain.log;

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
public class Log {
    
    private Long id;
    private String subject;
    private String action;
    private String log;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

}
