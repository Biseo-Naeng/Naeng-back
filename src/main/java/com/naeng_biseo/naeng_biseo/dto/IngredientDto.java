package com.naeng_biseo.naeng_biseo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDto {
    private String name;
    private String category;
    private LocalDate expirationDate;
}
