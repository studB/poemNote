package com.studb.poemNote.controller;

import java.util.List;

import com.studb.poemNote.domain.log.LogDto;
import com.studb.poemNote.service.LogService;
import com.studb.poemNote.utils.ApiResultForm;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class LogController {
    
    private final LogService logService;

    @GetMapping("/log")
    public ApiResultForm<List<LogDto>> readFromLog(){
        return ApiResultForm.result(logService.findAll());
    }

}
