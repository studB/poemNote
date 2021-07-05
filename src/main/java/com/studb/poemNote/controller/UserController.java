package com.studb.poemNote.controller;

import java.util.List;

import com.studb.poemNote.domain.user.User;
import com.studb.poemNote.domain.user.UserDto;
import com.studb.poemNote.service.UserService;
import com.studb.poemNote.utils.ApiResultForm;
import com.studb.poemNote.utils.OperationResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    
    UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/")
    public String start(){
        return "Success";
    }

    @GetMapping("/user")
    public ApiResultForm<List<UserDto>> readFromUser(){
        return ApiResultForm.result(userService.findAll());
    }

    @PostMapping("/user")
    public ApiResultForm<UserDto> checkAuthor(@RequestBody String key){
        return ApiResultForm.result(userService.check(key));
    }
}
