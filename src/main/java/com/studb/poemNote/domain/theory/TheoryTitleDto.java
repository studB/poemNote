package com.studb.poemNote.domain.theory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class TheoryTitleDto {
    private String textId;
    private String title;
}
