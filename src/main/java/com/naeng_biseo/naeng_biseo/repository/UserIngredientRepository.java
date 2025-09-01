package com.naeng_biseo.naeng_biseo.repository;

import com.naeng_biseo.naeng_biseo.domain.entities.User;
import com.naeng_biseo.naeng_biseo.domain.entities.UserIngredient;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserIngredientRepository {
    @PersistenceContext
    private EntityManager em;

    public UserIngredient save(UserIngredient userIngredient) {
        em.persist(userIngredient);
        return userIngredient;
    }

    public List<UserIngredient> findByUserId(Integer userId) {
        return em.createQuery("SELECT ui FROM UserIngredient ui WHERE ui.user.userId = :userId", UserIngredient.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public UserIngredient findById(Integer userIngredientId) {
        return em.find(UserIngredient.class, userIngredientId);
    }

    public Optional<UserIngredient> findByIdOptional(Integer userId) {
        return Optional.ofNullable(findById(userId));
    }

    public void delete(UserIngredient userIngredient) {
        em.remove(userIngredient);
    }
}