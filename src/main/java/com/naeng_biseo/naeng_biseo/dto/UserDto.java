package com.naeng_biseo.naeng_biseo.dto;

import com.naeng_biseo.naeng_biseo.domain.enums.Gender;
import com.naeng_biseo.naeng_biseo.domain.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@AllArgsConstructor
public class UserDto {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Create{
        private String name;
        private Date birthDate;
        private String phoneNumber;
        private String email;
        private String nickname;
        private Gender gender;
        private String profilePicture;
        private String passWordHash;
        private UserStatus stats;

    }
}
