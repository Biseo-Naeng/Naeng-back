package com.naeng_biseo.naeng_biseo.domain.entities;

import jakarta.persistence.*;

import com.naeng_biseo.naeng_biseo.domain.enums.Unit;

@Entity
@Table(name = "RecipeIngredients")
public class RecipeIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer recipeIngredientId;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "recipeId")
    private Recipe recipe;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "ingredientId")
    private Ingredient ingredient;

    @Column
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private Unit unit;

}
