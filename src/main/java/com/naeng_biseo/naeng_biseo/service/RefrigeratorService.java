package com.naeng_biseo.naeng_biseo.service;

import com.naeng_biseo.naeng_biseo.domain.entities.Ingredient;
import com.naeng_biseo.naeng_biseo.domain.entities.User;
import com.naeng_biseo.naeng_biseo.domain.entities.UserIngredient;
import com.naeng_biseo.naeng_biseo.domain.enums.IngredientCategory;
import com.naeng_biseo.naeng_biseo.dto.refrigerator.AddUserIngredientRequest;
import com.naeng_biseo.naeng_biseo.dto.refrigerator.AddUserIngredientResponse;
import com.naeng_biseo.naeng_biseo.repository.IngredientRepository;
import com.naeng_biseo.naeng_biseo.repository.UserIngredientRepository;
import com.naeng_biseo.naeng_biseo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefrigeratorService {

    private final UserRepository userRepository;
    private final IngredientRepository ingredientRepository;
    private final UserIngredientRepository userIngredientRepository;

    @Transactional
    public AddUserIngredientResponse addUserIngredient(Integer userId, AddUserIngredientRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        IngredientCategory category;
        try {
            category = IngredientCategory.valueOf(request.getCategory());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("잘못된 카테고리 값입니다: " + request.getCategory());
        }

        Ingredient ingredient = ingredientRepository
                .findByNameAndCategory(request.getName(), category)
                .orElseGet(() -> {
                    Ingredient newIngredient = new Ingredient();
                    newIngredient.setName(request.getName());
                    newIngredient.setCategory(category);
                    return ingredientRepository.save(newIngredient);
                });

        UserIngredient userIngredient = new UserIngredient();
        userIngredient.setUser(user);
        userIngredient.setIngredient(ingredient);
        userIngredient.setInstructions(request.getInstruction());
        userIngredient.setExpirationDate(request.getExpirationDate());

        UserIngredient savedUserIngredient = userIngredientRepository.save(userIngredient);

        return new AddUserIngredientResponse(
                user.getUserId(),
                savedUserIngredient.getUserIngredientId(),
                ingredient.getName(),
                savedUserIngredient.getInstructions(),
                ingredient.getCategory().name(),
                savedUserIngredient.getExpirationDate()
        );
    }
}
