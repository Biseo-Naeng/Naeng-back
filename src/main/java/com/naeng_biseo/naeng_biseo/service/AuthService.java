package com.naeng_biseo.naeng_biseo.service;

import com.naeng_biseo.naeng_biseo.domain.entities.User;
import com.naeng_biseo.naeng_biseo.dto.JwtToken;
import com.naeng_biseo.naeng_biseo.dto.UserDto;
import com.naeng_biseo.naeng_biseo.repository.AuthRepository;
import com.naeng_biseo.naeng_biseo.repository.UserRepository;
import com.naeng_biseo.naeng_biseo.security.JwtUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository repository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final StringRedisTemplate redisTemplate;
    private final MailService mailService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final JwtUtil jwtUtil;

    @Transactional
    public Integer save(UserDto.Create userCreateDto){
        validateDuplicateUser(userCreateDto);
        String encryptedPassword = passwordEncoder.encode(userCreateDto.getPassword());
        User user = new User(userCreateDto, encryptedPassword);
        User saveUser= repository.save(user);
        return saveUser.getUserId();
    }

    private void validateDuplicateUser(UserDto.Create userCreateDto) {
        repository.findByEmailOptional(userCreateDto.getEmail())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("이미 존재하는 회원입니다.");
                });    }

    @Transactional
    public JwtToken login(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        JwtToken token = jwtUtil.generateToken(authentication);

        // Refresh Token Redis에 저장
        redisTemplate.opsForValue().set("refresh:user:" + username, token.getRefreshToken(), Duration.ofDays(7));

        return token;
    }

    @Transactional
    public void setAuthEmail(String email) throws MessagingException {
        mailService.sendAuthCodeEmail(email);
    }

    public boolean verifyAuthCode(String email, String inputCode) {
        String key = email;
        String savedCode = redisTemplate.opsForValue().get(key);

        if (savedCode != null && savedCode.trim().equals(inputCode.trim())) {
            redisTemplate.delete(key);
            return true;
        }

        return false;
    }
}
