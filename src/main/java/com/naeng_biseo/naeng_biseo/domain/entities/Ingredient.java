package com.naeng_biseo.naeng_biseo.domain.entities;

import com.naeng_biseo.naeng_biseo.domain.enums.IngredientCategory;
import jakarta.persistence.*;

@Entity
@Table(name = "Ingredients")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ingredientId;

    @Column(length = 20)
    private String name;

    @Enumerated(EnumType.STRING)
    private IngredientCategory category;

}
