package com.naeng_biseo.naeng_biseo.controller;

import com.naeng_biseo.naeng_biseo.dto.RefrigeratorDto;
import com.naeng_biseo.naeng_biseo.exception.BaseResponse;
import com.naeng_biseo.naeng_biseo.service.RefrigeratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/refrigerator")
@RequiredArgsConstructor
public class RefrigeratorController {
    private final RefrigeratorService service;

    @PostMapping("/{userId}")
    public BaseResponse addIngredient(@PathVariable Integer userId, @RequestBody RefrigeratorDto.AddIngredient addIngredientDto) {
        RefrigeratorDto.Response response = service.addIngredient(userId, addIngredientDto);
        return BaseResponse.success(response);
    }

    @GetMapping("/{userId}")
    public BaseResponse getUserIngredients(@PathVariable Integer userId, @RequestParam String v) {
        RefrigeratorDto.IngredientListResponse response = service.getUserIngredients(userId, v);
        return BaseResponse.success(response);
    }

    @PutMapping("/{userId}/{ingredientId}")
    public BaseResponse updateIngredient(
            @PathVariable Integer userId,
            @PathVariable Integer ingredientId,
            @RequestBody RefrigeratorDto.UpdateIngredient dto
    ) {
        RefrigeratorDto.Response response = service.updateIngredient(userId, ingredientId, dto);
        return BaseResponse.success(response);
    }

    @GetMapping("/{userId}/{ingredientId}")
    public BaseResponse getIngredient(
            @PathVariable Integer userId,
            @PathVariable Integer ingredientId
    ) {
        RefrigeratorDto.Response response = service.getIngredient(userId, ingredientId);
        return BaseResponse.success(response);
    }

    @DeleteMapping("/{userId}/{ingredientId}")
    public BaseResponse deleteIngredient(
            @PathVariable Integer userId,
            @PathVariable Integer ingredientId
    ) {
        service.deleteIngredient(userId, ingredientId);
        return BaseResponse.success("재료가 삭제되었습니다.");
    }
}
