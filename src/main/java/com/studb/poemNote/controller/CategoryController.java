package com.studb.poemNote.controller;

import java.util.List;

import com.studb.poemNote.domain.category.CategoryDto;
import com.studb.poemNote.service.CategoryService;
import com.studb.poemNote.utils.ApiResultForm;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class CategoryController {
    
    private final CategoryService categoryService;

    @GetMapping("/category")
    public ApiResultForm<List<CategoryDto>> readFromCategory(){
        return ApiResultForm.result(categoryService.findAll());
    }

}
