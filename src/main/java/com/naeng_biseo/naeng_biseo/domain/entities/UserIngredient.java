package com.naeng_biseo.naeng_biseo.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "UserIngredients")
@Getter
@Setter
public class UserIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userIngredientId;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "ingredientId")
    private Ingredient ingredient;

    @Column(length = 1024)
    private String instructions;

    @Temporal(TemporalType.DATE)
    private Date expirationDate;

    // getters/setters, 생성자 등
}

