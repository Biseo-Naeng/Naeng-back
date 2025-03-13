package com.naeng_biseo.naeng_biseo.repository;

import com.naeng_biseo.naeng_biseo.domain.entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Page<Recipe> findAll(Pageable pageable);
    List<Recipe> findByTitleContainingOrInstructionsContaining(String title, String instructions);
}
