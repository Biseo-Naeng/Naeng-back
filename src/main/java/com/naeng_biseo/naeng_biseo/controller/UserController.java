package com.naeng_biseo.naeng_biseo.controller;

import com.naeng_biseo.naeng_biseo.dto.JwtToken;
import com.naeng_biseo.naeng_biseo.dto.UserDto;
import com.naeng_biseo.naeng_biseo.exception.BaseResponse;
import com.naeng_biseo.naeng_biseo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.naeng_biseo.naeng_biseo.security.JwtUtil;

import java.time.Duration;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    private final StringRedisTemplate redisTemplate;
    private final JwtUtil jwtUtil;

    @GetMapping("/user")
    public BaseResponse findUser(){
        List<UserDto.Response> collect = service.findAll();
        return BaseResponse.success(collect);
    }

    @GetMapping("/user/profile")
    public BaseResponse findOneUser(@AuthenticationPrincipal UserDetails userDetails){
        UserDto.Response user = service.findOne(userDetails.getUsername());
        return BaseResponse.success(user);
    }

    @DeleteMapping("/user/profile")
    public BaseResponse deleteUser(@AuthenticationPrincipal UserDetails userDetails){
        service.delete(userDetails.getUsername());
        return BaseResponse.success("삭제 성공");
    }

    @PatchMapping("/auth/sign-up")
    public BaseResponse saveUser(@RequestBody UserDto.Create userCreateDto){
        Integer id = service.save(userCreateDto);
        return BaseResponse.success(id);
    }

    @PostMapping("/user/profile")
    public BaseResponse saveUser(@AuthenticationPrincipal UserDetails userDetails, UserDto.Update userUpdateDto){
        UserDto.Response updatedUser = service.update(userDetails.getUsername(), userUpdateDto);
        return BaseResponse.success(updatedUser);
    }

    @PostMapping("/auth/login")
    public BaseResponse saveUser(@RequestBody UserDto.Login userLoginDto){
        String username = userLoginDto.getUsername();
        String password = userLoginDto.getPassword();
        JwtToken jwtToken = service.login(username, password);
        return BaseResponse.success(jwtToken);
    }
    @PostMapping("/auth/logout")
    public BaseResponse logout(@AuthenticationPrincipal UserDetails userDetails,
                               @RequestHeader("Authorization") String bearerToken) {

        String accessToken = bearerToken.replace("Bearer ", "");
        Long expiration = jwtUtil.getExpiration(accessToken);
        String username = userDetails.getUsername();

        redisTemplate.opsForValue().set("blacklist:" + accessToken, "logout", Duration.ofMillis(expiration));
        redisTemplate.delete("refresh:user:" + username);

        return BaseResponse.success("로그아웃 완료");
    }
}
