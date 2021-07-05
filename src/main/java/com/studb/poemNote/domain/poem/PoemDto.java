package com.studb.poemNote.domain.poem;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PoemDto {
    
    private Long id;
    private String textId;
    private Boolean published;
    private int publishIndex;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime publishTime;
    private String title;
    private String body;
    private String tag;
    private String completedStatus;
    private String writtenStatus;
    private String valueStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime modifiedAt;

    public PoemDto(Poem source){
        BeanUtils.copyProperties(source, this, "publishTime", "createdAt", "modifiedAt");
        this.publishTime = source.getPublishTime() == null ? null : source.getPublishTime().toLocalDateTime();
        this.createdAt = source.getCreatedAt() == null ? null : source.getCreatedAt().toLocalDateTime();
        this.modifiedAt = source.getModifiedAt() == null ? null : source.getModifiedAt().toLocalDateTime();
    }

}
