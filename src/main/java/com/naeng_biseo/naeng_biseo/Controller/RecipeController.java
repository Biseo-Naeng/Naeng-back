package com.naeng_biseo.naeng_biseo.Controller;

import com.naeng_biseo.naeng_biseo.dto.recipe.*;
import com.naeng_biseo.naeng_biseo.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.List;

@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;
    @PostMapping
    public ResponseEntity<RecipeResponse> addRecipe(@RequestBody RecipeRequest request) {
        return ResponseEntity.ok(recipeService.createRecipe(request));
    }
    @GetMapping("/{recipe_id}")
    public ResponseEntity<RecipeResponse> getRecipe(@PathVariable int recipe_id) {
        return ResponseEntity.ok(recipeService.getRecipeById(recipe_id));
    }
    @PutMapping("/{recipe_id}")
    public ResponseEntity<RecipeResponse> updateRecipe(@PathVariable int recipe_id, @RequestBody RecipeRequest request) {
        return ResponseEntity.ok(recipeService.updateRecipe(recipe_id, request));
    }
    @DeleteMapping("/{recipe_id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable int recipe_id) {
        recipeService.deleteRecipe(recipe_id);
        return ResponseEntity.ok("레시피가 삭제되었습니다.");
    }
    @GetMapping
    public ResponseEntity<Page<RecipeResponse>> getAllRecipes(@RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(recipeService.getAllRecipes(page));
    }
    @GetMapping("/search")
    public ResponseEntity<List<RecipeResponse>> searchRecipes(@RequestParam String keyword) {
        return ResponseEntity.ok(recipeService.searchRecipes(keyword));
    }
