package com.naeng_biseo.naeng_biseo.domain.entities;

import com.naeng_biseo.naeng_biseo.dto.UserDto;
import jakarta.persistence.*;

import java.util.Date;

import com.naeng_biseo.naeng_biseo.domain.enums.Gender;
import com.naeng_biseo.naeng_biseo.domain.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(length = 20, nullable = false)
    private String name;

    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Column(length = 15, unique = true)
    private String phoneNumber;

    @Column(length = 50, unique = true)
    private String email;

    @Column(length = 50, unique = true)
    private String username; // 아이디 

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(length = 255)
    private String profilePicture;

    @Column(length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserStatus stats;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User(UserDto.Create userCreateDto, String encryptedPassword) {
        this.name = userCreateDto.getName();
        this.birthDate = userCreateDto.getBirthDate();
        this.phoneNumber = userCreateDto.getPhoneNumber();
        this.email= userCreateDto.getEmail();
        this.username = userCreateDto.getUsername();
        this.gender = userCreateDto.getGender();
        this.profilePicture= userCreateDto.getProfilePicture();
        this.password = encryptedPassword;
        this.role= userCreateDto.getRole();
        this.stats = userCreateDto.getStats();
    }

    public void change(UserDto.Update userUpdateDto) {
        this.name = userUpdateDto.getName();
        this.birthDate = userUpdateDto.getBirthDate();
        this.phoneNumber = userUpdateDto.getPhoneNumber();
        this.gender = userUpdateDto.getGender();
        this.profilePicture= userUpdateDto.getProfilePicture();
        this.stats = userUpdateDto.getStats();
    }
}
