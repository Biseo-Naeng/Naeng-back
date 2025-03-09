package com.naeng_biseo.naeng_biseo.dto;

import com.naeng_biseo.naeng_biseo.domain.entities.User;
import com.naeng_biseo.naeng_biseo.domain.enums.Gender;
import com.naeng_biseo.naeng_biseo.domain.enums.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Getter
    @Setter
    @AllArgsConstructor
    @Schema(name= "UserResponse")
    public static class Response{
        private Integer id;
        private String name;
        private Date birthDate;
        private String phoneNumber;
        private String email;
        private String nickname;
        private Gender gender;
        private String profilePicture;
        private UserStatus stats;

        public Response(User user){
            this.id = user.getUserId();
            this.name=user.getName();
            this.birthDate=user.getBirthDate();
            this.phoneNumber=user.getPhoneNumber();
            this.email=user.getEmail();
            this.nickname=user.getNickname();
            this.gender=user.getGender();
            this.profilePicture=user.getProfilePicture();
            this.stats=user.getStats();
            
        }
    }
}
