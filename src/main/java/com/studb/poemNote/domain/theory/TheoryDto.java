package com.studb.poemNote.domain.theory;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TheoryDto {

    private Long id;
    private String textId;
    private String title;
    private String body;
    private String tag;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime modifiedAt;
    
    public TheoryDto(Theory source){
        BeanUtils.copyProperties(source, this, "createdAt", "modifiedAt");
        this.createdAt = source.getCreatedAt().toLocalDateTime();
        this.modifiedAt = source.getModifiedAt() == null ? null : source.getModifiedAt().toLocalDateTime();
    }

}
