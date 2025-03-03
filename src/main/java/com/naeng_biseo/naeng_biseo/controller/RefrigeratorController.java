package com.naeng_biseo.naeng_biseo.controller;

import com.naeng_biseo.naeng_biseo.dto.refrigerator.*;
import com.naeng_biseo.naeng_biseo.service.RefrigeratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/refrigerator")
@RequiredArgsConstructor
public class RefrigeratorController {

    private final RefrigeratorService refrigeratorService;

    @PostMapping("/{user_id}")
    public AddUserIngredientResponse addIngredient(
            @PathVariable("user_id") int userId,
            @RequestBody AddUserIngredientRequest request
    ) {
        return refrigeratorService.addUserIngredient(userId, request);
    }
}
