package com.naeng_biseo.naeng_biseo.controller;

import com.naeng_biseo.naeng_biseo.service.RecipeImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/recipes/{recipe_id}/images")
@RequiredArgsConstructor
public class RecipeImageController {

    private final RecipeImageService recipeImageService;

    // 이미지 업로드
    // S3에 저장 후 URL -> DB
    @PostMapping
    public ResponseEntity<String> uploadRecipeImage(
            @PathVariable Integer recipe_id,
            @RequestParam("file") MultipartFile file) {
        String imageUrl = recipeImageService.uploadRecipeImage(recipe_id, file);
        return ResponseEntity.ok(imageUrl);
    }

    // 특정 레시피 이미지 조회
    @GetMapping
    public ResponseEntity<List<String>> getRecipeImages(@PathVariable Integer recipe_id) {
        return ResponseEntity.ok(recipeImageService.getRecipeImages(recipe_id));
    }

    // 이미지 삭제 
    @DeleteMapping
    public ResponseEntity<String> deleteRecipeImage(
            @PathVariable Integer recipe_id,
            @RequestParam("image_url") String imageUrl) {
        recipeImageService.deleteRecipeImage(recipe_id, imageUrl);
        return ResponseEntity.ok("이미지가 삭제되었습니다.");
    }
}
