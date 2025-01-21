package com.naeng_biseo.naeng_biseo.domain;

import com.naeng_biseo.naeng_biseo.dto.AuthDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Auth {
    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String password;
    private String userId;
    private LocalDate birthDate;
    private String phoneNumber;
    private String gender;
    private String nickname;

    public Auth(AuthDto.Create authCreateDto) {
        this.email=authCreateDto.getEmail();
        this.password= authCreateDto.getPassword();
        this.userId=authCreateDto.getUserId();
        this.birthDate=authCreateDto.getBirthDate();
        this.phoneNumber=authCreateDto.getPhoneNumber();
        this.gender=authCreateDto.getGender();
        this.nickname=authCreateDto.getNickname();
    }
}
