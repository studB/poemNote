package com.studb.poemNote.controller;

import java.util.List;

import com.studb.poemNote.domain.backup.BackupDto;
import com.studb.poemNote.service.BackupService;
import com.studb.poemNote.utils.ApiResultForm;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class BackupController {
    
    private final BackupService backupService;

    @GetMapping("/backup")
    public ApiResultForm<List<BackupDto>> readFromBackup(){
        return ApiResultForm.result(backupService.findAll());
    }

}
