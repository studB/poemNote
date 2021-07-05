package com.studb.poemNote.domain.line;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LineDto {
    
    private Long id;
    @NotBlank
    private String textId;
    private String body;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    public LineDto(Line source){
        BeanUtils.copyProperties(source, this, "createdAt");
        this.createdAt = source.getCreatedAt().toLocalDateTime();
    }

}
