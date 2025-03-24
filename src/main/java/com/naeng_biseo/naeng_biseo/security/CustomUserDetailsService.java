package com.naeng_biseo.naeng_biseo.security;

import com.naeng_biseo.naeng_biseo.domain.entities.User;
import com.naeng_biseo.naeng_biseo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    //DB에서 사용자 정보 불러오기
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmailOptional(email)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(email + "에 해당하는 회원을 찾을 수 없습니다."));
    }

    public UserDetails createUserDetails(User user) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}
