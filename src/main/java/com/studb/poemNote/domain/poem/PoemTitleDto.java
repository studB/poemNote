package com.studb.poemNote.domain.poem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PoemTitleDto {
    
    private String textId;
    private String title;
    private int pCount;

}
