package com.naeng_biseo.naeng_biseo.controller;

import com.naeng_biseo.naeng_biseo.dto.JwtToken;
import com.naeng_biseo.naeng_biseo.dto.UserDto;
import com.naeng_biseo.naeng_biseo.exception.BaseResponse;
import com.naeng_biseo.naeng_biseo.service.AuthService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.naeng_biseo.naeng_biseo.security.JwtUtil;

import java.time.Duration;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;
    private final StringRedisTemplate redisTemplate;
    private final JwtUtil jwtUtil;

    @PatchMapping("/sign-up")
    public BaseResponse saveUser(@RequestBody UserDto.Create userCreateDto){
        Integer id = service.save(userCreateDto);
        return BaseResponse.success(id);
    }

    @PostMapping("/login")
    public BaseResponse saveUser(@RequestBody UserDto.Login userLoginDto){
        String username = userLoginDto.getUsername();
        String password = userLoginDto.getPassword();
        JwtToken jwtToken = service.login(username, password);
        return BaseResponse.success(jwtToken);
    }
    @PostMapping("/logout")
    public BaseResponse logout(@AuthenticationPrincipal UserDetails userDetails,
                               @RequestHeader("Authorization") String bearerToken) {

        String accessToken = bearerToken.replace("Bearer ", "");
        Long expiration = jwtUtil.getExpiration(accessToken);
        String username = userDetails.getUsername();

        redisTemplate.opsForValue().set("blacklist:" + accessToken, "logout", Duration.ofMillis(expiration));
        redisTemplate.delete("refresh:user:" + username);

        return BaseResponse.success("로그아웃 완료");
    }

    @PostMapping("/send-auth-number")
    public ResponseEntity<BaseResponse> sendAuthNumber(@RequestParam String email) throws MessagingException {
        service.setAuthEmail(email);
        return ResponseEntity.ok(BaseResponse.success("인증 메일을 전송했습니다."));
    }

    @PostMapping("/verify-auth-number")
    public ResponseEntity<BaseResponse> verifyAuthNumber(@RequestParam String email,
                                                         @RequestParam String code) {
        boolean isValid = service.verifyAuthCode(email, code);

        if (isValid) {
            return ResponseEntity.ok(BaseResponse.success("인증 성공"));
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(BaseResponse.error(400, "인증번호가 올바르지 않습니다."));
        }
    }
}
