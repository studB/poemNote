package com.studb.poemNote.domain.opinion;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpinionDto {
    
    private Long id;
    private String textId;
    private String body;
    private String tag;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime modifiedAt;

    public OpinionDto(Opinion source){
        this.id = source.getId();
        this.textId = source.getTextId();
        this.body = source.getBody();
        this.tag = source.getTag();
        this.createdAt = source.getCreatedAt().toLocalDateTime();
        this.modifiedAt = source.getModifiedAt() == null ? null : source.getModifiedAt().toLocalDateTime();
    }

}
