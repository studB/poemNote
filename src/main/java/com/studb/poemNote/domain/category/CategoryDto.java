package com.studb.poemNote.domain.category;

import org.springframework.beans.BeanUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
    
    private Long id;
    private String category;
    private int accessLevel;
    private Boolean publishable;
    private Boolean editable;
    private Boolean titleExist;
    private Boolean tagExist;
    private Boolean completedStatusExist;
    private Boolean writtenStatusExist;
    private Boolean valueStatusExist;

    public CategoryDto(Category source){
        BeanUtils.copyProperties(source, this);
    }
    
}
