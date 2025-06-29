package com.naeng_biseo.naeng_biseo.service;

import com.naeng_biseo.naeng_biseo.domain.entities.Ingredient;
import com.naeng_biseo.naeng_biseo.domain.entities.User;
import com.naeng_biseo.naeng_biseo.domain.entities.UserIngredient;
import com.naeng_biseo.naeng_biseo.domain.enums.IngredientCategory;
import com.naeng_biseo.naeng_biseo.dto.RefrigeratorDto;
import com.naeng_biseo.naeng_biseo.repository.IngredientRepository;
import com.naeng_biseo.naeng_biseo.repository.UserIngredientRepository;
import com.naeng_biseo.naeng_biseo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class RefrigeratorService {
    private final UserRepository userRepository;
    private final IngredientRepository ingredientRepository;
    private final UserIngredientRepository userIngredientRepository;

    @Transactional
    public RefrigeratorDto.Response addIngredient(Integer userId, RefrigeratorDto.AddIngredient addIngredientDto) {
        // 사용자 조회
        User user = userRepository.findByIdOptional(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 사용자를 찾을 수 없습니다."));

        // 재료 조회 또는 생성
        IngredientCategory category = addIngredientDto.getCategoryEnum();
        Ingredient ingredient = ingredientRepository.findByNameAndCategory(addIngredientDto.getName(), category);
        
        if (ingredient == null) {
            ingredient = new Ingredient(addIngredientDto.getName(), category);
            ingredient = ingredientRepository.save(ingredient);
        }

        // 사용자 재료 생성
        UserIngredient userIngredient = new UserIngredient(
                user, 
                ingredient, 
                addIngredientDto.getInstruction(), 
                addIngredientDto.getExpirationDate()
        );
        
        userIngredient = userIngredientRepository.save(userIngredient);

        return new RefrigeratorDto.Response(userIngredient);
    }
}
