package com.naeng_biseo.naeng_biseo.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor  
@Table(name = "UserIngredients")
public class UserIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userIngredientId;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Setter
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "ingredientId")
    private Ingredient ingredient;

    @Setter
    @Column(length = 1024)
    private String instructions;

    @Setter
    private LocalDate expirationDate;

    public UserIngredient(User user, Ingredient ingredient, String instructions, LocalDate expirationDate) {
        this.user = user;
        this.ingredient = ingredient;
        this.instructions = instructions;
        this.expirationDate = expirationDate;
    }
}

