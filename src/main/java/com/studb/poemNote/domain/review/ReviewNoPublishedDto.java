package com.studb.poemNote.domain.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ReviewNoPublishedDto {
    
    private Long id;
    private String textId;
    private String title;
    private String body;
    private String tag;
    private String writtenStatus;
    int pCount;
}
