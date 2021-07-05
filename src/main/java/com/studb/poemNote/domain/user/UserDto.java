package com.studb.poemNote.domain.user;

import org.springframework.beans.BeanUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    
    private Long id;
    private String role;
    private int accessPower;
    private String key;
    private Boolean editable;
    private Boolean readable;

    public UserDto(User source){
        BeanUtils.copyProperties(source, this);
    }

}
