package com.naeng_biseo.naeng_biseo.dto;

import com.naeng_biseo.naeng_biseo.domain.entities.Role;
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
        private String username;
        private Gender gender;
        private String profilePicture;
        private String password;
        private Role role;
        private UserStatus stats;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Update{
        private String name;
        private Date birthDate;
        private String phoneNumber;
        private String username;
        private Gender gender;
        private String profilePicture;
        private UserStatus stats;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Login{
        private String username;
        private String password;
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
        private String username;
        private Gender gender;
        private String profilePicture;
        private UserStatus stats;
        private Role role;

        public Response(User user){
            this.id = user.getUserId();
            this.name=user.getName();
            this.birthDate=user.getBirthDate();
            this.phoneNumber=user.getPhoneNumber();
            this.email=user.getEmail();
            this.username=user.getUsername();
            this.gender=user.getGender();
            this.profilePicture=user.getProfilePicture();
            this.stats=user.getStats();
            this.role = user.getRole();
        }
    }
}
