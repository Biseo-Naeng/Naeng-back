package com.naeng_biseo.naeng_biseo.service;

import com.naeng_biseo.naeng_biseo.domain.Auth;
import com.naeng_biseo.naeng_biseo.dto.AuthDto;
import com.naeng_biseo.naeng_biseo.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository repository;

    public Long save(AuthDto.Create authCreateDto) {
        Auth auth=new Auth(authCreateDto);
        Auth saveAuth=repository.save(auth);
        return saveAuth.getId();
    }
}
