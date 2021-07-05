package com.studb.poemNote.domain.backup;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BackupDto {
    
    private Long id;
    private int categoryId;
    @NotBlank
    private String textId;
    private int publishIndex;
    private String title;
    private String body;
    private String tag;
    private String completedStatus;
    private String writtenStatus;
    private String valueStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime time; 

    public BackupDto(Backup source){
        BeanUtils.copyProperties(source, this, "time");
        this.time = source.getTime().toLocalDateTime();
    }
    
}
