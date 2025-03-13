
package com.naeng_biseo.naeng_biseo.service;

import com.naeng_biseo.naeng_biseo.domain.entities.Recipe;
import com.naeng_biseo.naeng_biseo.dto.recipe.RecipeRequest;
import com.naeng_biseo.naeng_biseo.dto.recipe.RecipeResponse;
import com.naeng_biseo.naeng_biseo.repository.RecipeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;

    @Transactional
    public RecipeResponse createRecipe(RecipeRequest request) {
        Recipe recipe = new Recipe();
        recipe.setTitle(request.getTitle());
        recipe.setInstructions(request.getInstructions());
        recipe.setUserId(request.getUserId());
        recipe.setWriteDate(new Date());
        recipe.setView(0);

        Recipe savedRecipe = recipeRepository.save(recipe);

        return new RecipeResponse(
                savedRecipe.getRecipeId(),
                savedRecipe.getTitle(),
                savedRecipe.getInstructions(),
                savedRecipe.getUserId(),
                savedRecipe.getWriteDate(),
                savedRecipe.getView()
        );
    }

    public RecipeResponse getRecipeById(int id) {
        Recipe recipe = recipeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("레시피를 찾을 수 없습니다."));

        recipe.setView(recipe.getView() + 1);
        recipeRepository.save(recipe);

        return new RecipeResponse(
                recipe.getRecipeId(),
                recipe.getTitle(),
                recipe.getInstructions(),
                recipe.getUserId(),
                recipe.getWriteDate(),
                recipe.getView()
        );
    }

    public Page<RecipeResponse> getAllRecipes(int page) {
        Page<Recipe> recipes = recipeRepository.findAll(PageRequest.of(page, 10));
        return recipes.map(recipe -> new RecipeResponse(
                recipe.getRecipeId(),
                recipe.getTitle(),
                recipe.getInstructions(),
                recipe.getUserId(),
                recipe.getWriteDate(),
                recipe.getView()
        ));
    }
}

