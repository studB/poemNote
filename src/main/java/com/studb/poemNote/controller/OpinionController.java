package com.studb.poemNote.controller;

import java.util.List;

import com.studb.poemNote.domain.opinion.Opinion;
import com.studb.poemNote.domain.opinion.OpinionDto;
import com.studb.poemNote.domain.opinion.OpinionTitleDto;
import com.studb.poemNote.service.OpinionService;
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
public class OpinionController {
    
    private final OpinionService opinionService;

    @GetMapping("/opinion")
    public ApiResultForm<List<OpinionDto>> readFromOpinion(){
        return ApiResultForm.result(opinionService.findAll());
    }

    @GetMapping("/opinion/{textId}")
    public ApiResultForm<OpinionDto> readOneFromOpinion(@PathVariable String textId) {
        return ApiResultForm.result(opinionService.findAny(textId));
    }

    @GetMapping("/opinion/title")
    public ApiResultForm<List<OpinionTitleDto>> readTitleFromOpinion() {
        return ApiResultForm.result(opinionService.findOpinionTitle());
    }

    @PostMapping("/opinion")
    public ApiResultForm<OperationResult> writeOpinion(@RequestBody Opinion opinion){
        return ApiResultForm.result(opinionService.save(opinion));
    }

    @DeleteMapping("/opinion/{textId}")
    public ApiResultForm<OperationResult> eraseOpinion(@PathVariable String textId){
        return ApiResultForm.result(opinionService.delete(textId));
    }

    @PutMapping("/opinion")
    public ApiResultForm<OperationResult> rewriteOpinion(@RequestBody Opinion opinion){
        return ApiResultForm.result(opinionService.update(opinion));
    }
}
