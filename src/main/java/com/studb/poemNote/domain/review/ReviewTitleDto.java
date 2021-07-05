package com.studb.poemNote.domain.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ReviewTitleDto {
    
    private String textId;
    private String title;
    private int pCount;
}
