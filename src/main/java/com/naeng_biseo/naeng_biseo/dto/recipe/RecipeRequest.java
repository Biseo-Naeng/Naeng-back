package com.naeng_biseo.naeng_biseo.dto.recipe;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeRequest {
    private String title;
    private String instructions;
    private Integer userId;
}
