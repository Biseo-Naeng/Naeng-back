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

import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional(readOnly = true)
    public RefrigeratorDto.IngredientListResponse getUserIngredients(Integer userId, String category) {
        // 사용자 조회
        User user = userRepository.findByIdOptional(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 사용자를 찾을 수 없습니다."));

        // 사용자의 재료 리스트 조회
        List<UserIngredient> userIngredients = userIngredientRepository.findByUserId(userId);

        // 카테고리 필터링
        IngredientCategory filterCategory = getCategoryFromString(category);
        userIngredients = userIngredients.stream()
                .filter(ui -> ui.getIngredient().getCategory() == filterCategory)
                .collect(Collectors.toList());

        // DTO로 변환
        List<RefrigeratorDto.IngredientListResponse.IngredientInfo> ingredientInfoList = 
                userIngredients.stream()
                        .map(RefrigeratorDto.IngredientListResponse.IngredientInfo::new)
                        .collect(Collectors.toList());

        return new RefrigeratorDto.IngredientListResponse(
                userId,
                userId, // targetUserId와 userId가 같음
                ingredientInfoList.size(),
                ingredientInfoList
        );
    }

    private IngredientCategory getCategoryFromString(String category) {
        switch (category) {
            case "냉동":
                return IngredientCategory.FROZEN;
            case "냉장":
                return IngredientCategory.REFRIGERATED;
            case "실온":
                return IngredientCategory.AMBIENT;
            case "조미료":
                return IngredientCategory.SEASONING;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "올바르지 않은 카테고리입니다.");
        }
    }

    @Transactional
    public RefrigeratorDto.Response updateIngredient(Integer userId, Integer ingredientId, RefrigeratorDto.UpdateIngredient dto) {
        User user = userRepository.findByIdOptional(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 사용자를 찾을 수 없습니다."));

        UserIngredient userIngredient = userIngredientRepository.findByIdOptional(ingredientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 재료를 찾을 수 없습니다."));

        if (!userIngredient.getUser().getUserId().equals(user.getUserId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인 재료만 수정할 수 있습니다.");
        }

        if (dto.getInstruction() != null) {
            userIngredient.setInstructions(dto.getInstruction());
        }
        if (dto.getExpirationDate() != null) {
            userIngredient.setExpirationDate(dto.getExpirationDate());
        }
        if (dto.getName() != null && dto.getCategory() != null) {
            IngredientCategory category = dto.getCategoryEnum();
            Ingredient ingredient = ingredientRepository.findByNameAndCategory(dto.getName(), category);
            if (ingredient == null) {
                ingredient = ingredientRepository.save(new Ingredient(dto.getName(), category));
            }
            userIngredient.setIngredient(ingredient);
        }

        return new RefrigeratorDto.Response(userIngredient);
    }

    @Transactional(readOnly = true)
    public RefrigeratorDto.Response getIngredient(Integer userId, Integer ingredientId) {
        // 사용자 확인
        User user = userRepository.findByIdOptional(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 사용자를 찾을 수 없습니다."));

        // 해당 user가 등록한 재료 찾기
        UserIngredient userIngredient = userIngredientRepository.findByIdOptional(ingredientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 재료를 찾을 수 없습니다."));

        if (!userIngredient.getUser().getUserId().equals(user.getUserId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인 재료만 조회할 수 있습니다.");
        }

        return new RefrigeratorDto.Response(userIngredient);
    }
    @Transactional
    public void deleteIngredient(Integer userId, Integer ingredientId) {
        // 사용자 확인
        User user = userRepository.findByIdOptional(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 사용자를 찾을 수 없습니다."));

        // 사용자 재료 확인
        UserIngredient userIngredient = userIngredientRepository.findByIdOptional(ingredientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 재료를 찾을 수 없습니다."));

        if (!userIngredient.getUser().getUserId().equals(user.getUserId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인 재료만 삭제할 수 있습니다.");
        }

        // 삭제 실행
        userIngredientRepository.delete(userIngredient);
    }

}
