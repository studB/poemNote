package com.studb.poemNote.domain.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class Category {
    
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

}
