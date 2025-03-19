package com.naeng_biseo.naeng_biseo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.naeng_biseo.naeng_biseo.domain.entities.Recipe;
import com.naeng_biseo.naeng_biseo.repository.RecipeRepository;
import com.naeng_biseo.naeng_biseo.utils.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeImageService {

    private final RecipeRepository recipeRepository;
    private final S3Uploader s3Uploader;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // 레시피 이미지 업로드
    public String uploadRecipeImage(Integer recipeId, MultipartFile file) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("레시피가 존재하지 않습니다."));

        String imageUrl = s3Uploader.uploadFile(file);

        List<String> imageUrls = getRecipeImages(recipeId);
        imageUrls.add(imageUrl);

        try {
            recipe.setImageUrls(objectMapper.writeValueAsString(imageUrls));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("이미지 리스트 저장 오류", e);
        }

        recipeRepository.save(recipe);
        return imageUrl;
    }

    // 이미지 리스트 조회
    public List<String> getRecipeImages(Integer recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("레시피가 존재하지 않습니다."));

        try {
            return objectMapper.readValue(recipe.getImageUrls(), List.class);
        } catch (JsonProcessingException e) {
            return new ArrayList<>();
        }
    }

    // 이미지 삭제
    public void deleteRecipeImage(Integer recipeId, String imageUrl) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("레시피가 존재하지 않습니다."));

        List<String> imageUrls = getRecipeImages(recipeId);
        imageUrls.remove(imageUrl);

        try {
            recipe.setImageUrls(objectMapper.writeValueAsString(imageUrls));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("이미지 리스트 갱신 오류", e);
        }

        s3Uploader.deleteFile(imageUrl);
        recipeRepository.save(recipe);
    }
}
