package com.studb.poemNote.domain.opinion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class OpinionTitleDto {
    
    private String textId;
    private String title;

    public static String refineBodyToTitle(String body){
        int length = body.length();
        return length < 10 ? body : body.substring(0, 9) + "..." ;
    }

}
