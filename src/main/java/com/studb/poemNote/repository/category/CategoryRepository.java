package com.studb.poemNote.repository.category;

import java.util.List;

import com.studb.poemNote.domain.category.Category;

public interface CategoryRepository {
    
    List<Category> findAll();

}
