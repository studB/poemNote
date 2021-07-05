package com.studb.poemNote.service;

import java.util.List;
import java.util.stream.Collectors;

import com.studb.poemNote.domain.category.CategoryDto;
import com.studb.poemNote.error.Exception.BreakDatabaseWorkingException;
import com.studb.poemNote.repository.category.CategoryRepository;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryService {
    
    private final CategoryRepository categoryRepository;

    public List<CategoryDto> findAll(){
        try{
            return categoryRepository.findAll().stream()
                    .map(CategoryDto::new)
                    .collect(Collectors.toList());
        }catch(Exception e){
            if( e.getCause() instanceof Throwable){
                throw new BreakDatabaseWorkingException(e.getCause().getMessage(), e.getCause());
            }else{
                throw new BreakDatabaseWorkingException(e.getMessage());
            }
        }
    }

}
