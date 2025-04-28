package com.naeng_biseo.naeng_biseo.service;

import com.naeng_biseo.naeng_biseo.domain.entities.User;
import com.naeng_biseo.naeng_biseo.dto.JwtToken;
import com.naeng_biseo.naeng_biseo.dto.UserDto;
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
public class UserService {
    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<UserDto.Response> findAll(){
        List<User> user = repository.findAll();
        return user.stream().map(UserDto.Response::new).collect(Collectors.toList());
    }

    public UserDto.Response findOne(String email){
        User user = repository.findByEmail(email);
        return new UserDto.Response(user);
    }

    public void delete(String email){
        User user = repository.findByEmail(email);
        repository.remove(user);

    }

    @Transactional
    public UserDto.Response update(String email, UserDto.Update userUpdateDto) {
        User user = repository.findByEmailOptional(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 이메일을 찾을 수 없습니다."));
        user.change(userUpdateDto);
        return new UserDto.Response(user);
    }

    public String findUserId(UserDto.FindUserId findUserIdDto) {
        User user = repository.findByEmail(findUserIdDto.getEmail());
        if (user == null || !user.getName().equals(findUserIdDto.getName())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 사용자를 찾을 수 없습니다.");
        }
        return user.getUsername();
    }

    @Transactional
    public void changePassword(UserDto.ChangePassword changePasswordDto) {
        User user = repository.findByEmail(changePasswordDto.getEmail());
        if (user == null || !user.getUsername().equals(changePasswordDto.getUsername()) || 
            !user.getName().equals(changePasswordDto.getName())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 사용자를 찾을 수 없습니다.");
        }
                
        user.changePassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
    }
}
