package com.naeng_biseo.naeng_biseo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class AuthDto {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Create{
        private String email;
        private String password;
        private String userId;
        private LocalDate birthDate;
        private String phoneNumber;
        private String gender;
        private String nickname;
    }
}
