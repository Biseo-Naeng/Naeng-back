package com.naeng_biseo.naeng_biseo.dto.refrigerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddUserIngredientResponse {

    private Integer userId;
    private Integer id;
    private String name;
    private String instruction;
    private String category;
    private Date expirationDate;
}
