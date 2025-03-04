package com.naeng_biseo.naeng_biseo.domain.entities;

import com.naeng_biseo.naeng_biseo.domain.enums.IngredientCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Ingredients")
@Getter
@Setter
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ingredientId;

    @Column(length = 20)
    private String name;

    @Enumerated(EnumType.STRING)
    private IngredientCategory category;

}
