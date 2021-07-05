package com.studb.poemNote.domain.poem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PoemNoPublishedDto {
    
    private Long id;
    private String textId;
    private String title;
    private String body;
    private String tag;
    private String writtenStatus;
    int pCount;

}
