package com.naeng_biseo.naeng_biseo.domain.entities;

import com.naeng_biseo.naeng_biseo.domain.enums.IngredientCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Ingredients")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ingredientId;

    @Column(length = 20)
    private String name;

    @Enumerated(EnumType.STRING)
    private IngredientCategory category;

    public Ingredient(String name, IngredientCategory category) {
        this.name = name;
        this.category = category;
    }
}
