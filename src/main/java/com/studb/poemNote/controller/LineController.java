package com.studb.poemNote.controller;

import java.util.List;

import com.studb.poemNote.domain.line.Line;
import com.studb.poemNote.domain.line.LineDto;
import com.studb.poemNote.service.LineService;
import com.studb.poemNote.utils.ApiResultForm;
import com.studb.poemNote.utils.OperationResult;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class LineController {
    
    private final LineService lineService;

    @GetMapping("/line")
    public ApiResultForm<List<LineDto>> readFromLine(){
        return ApiResultForm.result(lineService.findAll());
    }

    @GetMapping("/line/{textId}")
    public ApiResultForm<LineDto> readOneFromLine(@PathVariable String textId){
        return ApiResultForm.result(lineService.findAny(textId));
    }

    // curl -X POST -H "content-type:application/json" -d '{"textId": "l012", "body": "My Line"}'   localhost:8080/api/v1/line
    @PostMapping("/line")
    public ApiResultForm<OperationResult> writeLine(@RequestBody Line line){
        return ApiResultForm.result(lineService.save(line));
    }

    @DeleteMapping("/line/{textId}")
    public ApiResultForm<OperationResult> eraseLine(@PathVariable String textId){
        return ApiResultForm.result(lineService.delete(textId));
    }

}
