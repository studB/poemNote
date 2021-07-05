package com.studb.poemNote.controller;

import java.util.List;

import com.studb.poemNote.domain.theory.Theory;
import com.studb.poemNote.domain.theory.TheoryDto;
import com.studb.poemNote.domain.theory.TheoryTitleDto;
import com.studb.poemNote.service.TheoryService;
import com.studb.poemNote.utils.ApiResultForm;
import com.studb.poemNote.utils.OperationResult;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class TheoryController {
    
    private final TheoryService theoryService;

    @GetMapping("/theory")
    public ApiResultForm<List<TheoryDto>> readFromTheory(){
        return ApiResultForm.result(theoryService.findAll());
    }

    @GetMapping("/theory/{textId}")
    public ApiResultForm<TheoryDto> readOneFromTheory(@PathVariable String textId){
        return ApiResultForm.result(theoryService.findAny(textId));
    }

    @GetMapping("/theory/title")
    public ApiResultForm<List<TheoryTitleDto>> readTitleFromTheory() {
        return ApiResultForm.result(theoryService.findTheoryTitle());
    }

    @PostMapping("/theory")
    public ApiResultForm<OperationResult> writeTheory(@RequestBody Theory theory){
        return ApiResultForm.result(theoryService.save(theory));
    }

    @DeleteMapping("/theory/{textId}")
    public ApiResultForm<OperationResult> eraseTheory(@PathVariable String textId){
        return ApiResultForm.result(theoryService.delete(textId));
    }

    @PutMapping("/theory")
    public ApiResultForm<OperationResult> rewriteTheory(@RequestBody Theory theory){
        return ApiResultForm.result(theoryService.update(theory));
    }

}
