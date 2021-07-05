package com.studb.poemNote.controller;

import java.util.List;

import com.studb.poemNote.domain.poem.Poem;
import com.studb.poemNote.domain.poem.PoemDto;
import com.studb.poemNote.domain.poem.PoemNoPublishedDto;
import com.studb.poemNote.domain.poem.PoemTitleDto;
import com.studb.poemNote.service.PoemService;
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
public class PoemController {
    
    private final PoemService poemService;

    @GetMapping("/poem")
    public ApiResultForm<List<PoemDto>> readFromPoem(){
        return ApiResultForm.result(poemService.findAll());
    }

    @GetMapping("/poem/{textId}")
    public ApiResultForm<PoemDto> readOneFromPoem(@PathVariable String textId){
        return ApiResultForm.result(poemService.findAny(textId));
    }

    @GetMapping("/poem/title")
    public ApiResultForm<List<PoemTitleDto>> readTitleFromPoem(){
        return ApiResultForm.result(poemService.findPoemTitle());
    }

    @GetMapping("/poem/nopublished")
    public ApiResultForm<List<PoemNoPublishedDto>> readNoPublishedFromPoem(){
        return ApiResultForm.result(poemService.findAllNoPublished());
    }

    @GetMapping("/poem/published/{textId}")
    public ApiResultForm<List<PoemDto>> readPublishedFromPoem(@PathVariable String textId){
        return ApiResultForm.result(poemService.findAllPublished(textId));
    }

    @PostMapping("/poem")
    public ApiResultForm<OperationResult> writePoem(@RequestBody Poem poem){
        return ApiResultForm.result(poemService.save(poem));
    }

    @DeleteMapping("/poem/{textId}")
    public ApiResultForm<OperationResult> erasePoem(@PathVariable String textId){
        return ApiResultForm.result(poemService.delete(textId));
    }

    @PutMapping("/poem")
    public ApiResultForm<OperationResult> rewritePoem(@RequestBody Poem poem){
        return ApiResultForm.result(poemService.update(poem));
    }

    @PostMapping("/poem/publish")
    public ApiResultForm<OperationResult> publishPoem(@RequestBody Poem poem){
        return ApiResultForm.result(poemService.publish(poem));
    }

    @PutMapping("/poem/publish")
    public ApiResultForm<OperationResult> rewritePublishedPoem(@RequestBody Poem poem){
        return ApiResultForm.result(poemService.updatePublished(poem));
    }
}
