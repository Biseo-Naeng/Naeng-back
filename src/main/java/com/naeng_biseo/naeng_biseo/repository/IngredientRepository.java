package com.naeng_biseo.naeng_biseo.repository;

import com.naeng_biseo.naeng_biseo.domain.entities.Ingredient;
import com.naeng_biseo.naeng_biseo.domain.enums.IngredientCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
    Optional<Ingredient> findByNameAndCategory(String name, IngredientCategory category);
}
