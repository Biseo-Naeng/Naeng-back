package com.naeng_biseo.naeng_biseo.dto.recipe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class RecipeResponse {
    private Integer recipeId;
    private String title;
    private String instructions;
    private Integer userId;
    private Date writeDate;
    private Integer view;
}
