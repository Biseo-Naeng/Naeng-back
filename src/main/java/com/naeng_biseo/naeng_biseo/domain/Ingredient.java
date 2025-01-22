package com.naeng_biseo.naeng_biseo.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String category; // 냉동, 냉장, 실온 등
    private LocalDate expirationDate; // 유통기한
    private Long ownerId; // 회원 ID (Foreign Key)
}
