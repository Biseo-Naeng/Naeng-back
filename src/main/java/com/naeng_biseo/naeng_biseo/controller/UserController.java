package com.naeng_biseo.naeng_biseo.controller;

import com.naeng_biseo.naeng_biseo.dto.UserDto;
import com.naeng_biseo.naeng_biseo.exception.BaseResponse;
import com.naeng_biseo.naeng_biseo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping
    public BaseResponse findUser(){
        List<UserDto.Response> collect = service.findAll();
        return BaseResponse.success(collect);
    }

    @PostMapping("/user")
    public BaseResponse saveUser(@RequestBody UserDto.Create userCreateDto){
        Integer id = service.save(userCreateDto);
        return BaseResponse.success(id);
    }

}
