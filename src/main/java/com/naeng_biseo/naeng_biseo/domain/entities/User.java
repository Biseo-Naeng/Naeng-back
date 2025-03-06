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

    @Column(length = 16, unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(length = 255)
    private String profilePicture;

    @Column(length = 255)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private UserStatus stats;

    public User(UserDto.Create userCreateDto, String encryptedPassword) {
        this.name = userCreateDto.getName();
        this.birthDate = userCreateDto.getBirthDate();
        this.phoneNumber = userCreateDto.getPhoneNumber();
        this.email= userCreateDto.getEmail();
        this.nickname= userCreateDto.getNickname();
        this.gender = userCreateDto.getGender();
        this.profilePicture= userCreateDto.getProfilePicture();
        this.passwordHash = encryptedPassword;
        this.stats = userCreateDto.getStats();
    }
}
