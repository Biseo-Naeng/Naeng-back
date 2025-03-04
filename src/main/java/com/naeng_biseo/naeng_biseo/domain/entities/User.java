package com.naeng_biseo.naeng_biseo.domain.entities;

import jakarta.persistence.*;
import java.util.Date;

import com.naeng_biseo.naeng_biseo.domain.enums.Gender;
import com.naeng_biseo.naeng_biseo.domain.enums.UserStatus;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Users")
@Getter
@Setter
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

}
