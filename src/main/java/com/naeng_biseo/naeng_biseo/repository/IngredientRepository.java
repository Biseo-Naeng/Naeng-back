package com.naeng_biseo.naeng_biseo.repository;

import com.naeng_biseo.naeng_biseo.domain.entities.Ingredient;
import com.naeng_biseo.naeng_biseo.domain.enums.IngredientCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IngredientRepository {
    @PersistenceContext
    private EntityManager em;

    public Ingredient save(Ingredient ingredient) {
        em.persist(ingredient);
        return ingredient;
    }

    public Ingredient findByNameAndCategory(String name, IngredientCategory category) {
        List<Ingredient> ingredients = em.createQuery("SELECT i FROM Ingredient i WHERE i.name = :name AND i.category = :category", Ingredient.class)
                .setParameter("name", name)
                .setParameter("category", category)
                .getResultList();
        return ingredients.isEmpty() ? null : ingredients.get(0);
    }

    public Ingredient findById(Integer ingredientId) {
        return em.find(Ingredient.class, ingredientId);
    }
} 