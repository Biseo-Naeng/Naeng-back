package com.naeng_biseo.naeng_biseo.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

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

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "ingredientId")
    private Ingredient ingredient;

    @Column(length = 1024)
    private String instructions;

    @Temporal(TemporalType.DATE)
    private Date expirationDate;

    public UserIngredient(User user, Ingredient ingredient, String instructions, Date expirationDate) {
        this.user = user;
        this.ingredient = ingredient;
        this.instructions = instructions;
        this.expirationDate = expirationDate;
    }
}

