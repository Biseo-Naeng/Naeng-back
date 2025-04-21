package com.naeng_biseo.naeng_biseo.controller;

import com.naeng_biseo.naeng_biseo.dto.UserDto;
import com.naeng_biseo.naeng_biseo.exception.BaseResponse;
import com.naeng_biseo.naeng_biseo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping()
    public BaseResponse findUser(){
        List<UserDto.Response> collect = service.findAll();
        return BaseResponse.success(collect);
    }

    @GetMapping("/profile")
    public BaseResponse findOneUser(@AuthenticationPrincipal UserDetails userDetails){
        UserDto.Response user = service.findOne(userDetails.getUsername());
        return BaseResponse.success(user);
    }

    @DeleteMapping("/profile")
    public BaseResponse deleteUser(@AuthenticationPrincipal UserDetails userDetails){
        service.delete(userDetails.getUsername());
        return BaseResponse.success("삭제 성공");
    }

    @PostMapping("/profile")
    public BaseResponse saveUser(@AuthenticationPrincipal UserDetails userDetails, UserDto.Update userUpdateDto){
        UserDto.Response updatedUser = service.update(userDetails.getUsername(), userUpdateDto);
        return BaseResponse.success(updatedUser);
    }
}
