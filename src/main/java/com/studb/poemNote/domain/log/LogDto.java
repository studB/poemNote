package com.studb.poemNote.domain.log;

import org.springframework.beans.BeanUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogDto {
    
    private Long id;
    private String subject;
    private String action;
    private String log;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    public LogDto(Log source){
        BeanUtils.copyProperties(source, this);
    }
    
}
