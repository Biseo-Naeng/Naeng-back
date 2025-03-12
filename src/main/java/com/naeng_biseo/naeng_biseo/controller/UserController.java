package com.naeng_biseo.naeng_biseo.controller;

import com.naeng_biseo.naeng_biseo.dto.UserDto;
import com.naeng_biseo.naeng_biseo.exception.BaseResponse;
import com.naeng_biseo.naeng_biseo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {
    private final UserService service;

    @GetMapping("/user")
    public BaseResponse findUser(){
        List<UserDto.Response> collect = service.findAll();
        return BaseResponse.success(collect);
    }

    @GetMapping("/user/{id}")
    public BaseResponse findOneUser(Long id){
        UserDto.Response user = service.findOne(id);
        return BaseResponse.success(user);
    }

    @PostMapping("/user")
    public BaseResponse saveUser(@RequestBody UserDto.Create userCreateDto){
        Integer id = service.save(userCreateDto);
        return BaseResponse.success(id);
    }

    @PostMapping("/login")
    public BaseResponse saveUser(@RequestBody UserDto.Login userLoginDto){
        String email = userLoginDto.getEmail();
        String passWordHash = userLoginDto.getPassWordHash();
        Integer userId = service.login(email, passWordHash);
        return BaseResponse.success("로그인에 성공했습니다.");
    }
}
