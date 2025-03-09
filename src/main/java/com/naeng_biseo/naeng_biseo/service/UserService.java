package com.naeng_biseo.naeng_biseo.service;

import com.naeng_biseo.naeng_biseo.domain.entities.User;
import com.naeng_biseo.naeng_biseo.dto.UserDto;
import com.naeng_biseo.naeng_biseo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Integer save(UserDto.Create userCreateDto){
        validateDuplicateUser(userCreateDto);
        String encryptedPassword = passwordEncoder.encode(userCreateDto.getPassWordHash());
        User user = new User(userCreateDto, encryptedPassword);
        User saveUser= repository.save(user);
        return saveUser.getUserId();
    }
    private void validateDuplicateUser(UserDto.Create userCreateDto) {
        User findUser = repository.findByEmail(userCreateDto.getEmail());
        if (findUser != null) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public UserDto.Response findOne(Long id){
        User findOne = repository.findOne(id);
        return new UserDto.Response(findOne);
    }
}
