package com.naeng_biseo.naeng_biseo.dto;

import com.naeng_biseo.naeng_biseo.domain.entities.UserIngredient;
import com.naeng_biseo.naeng_biseo.domain.enums.IngredientCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

public class RefrigeratorDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddIngredient {
        private Integer userId;
        private String name;
        private String instruction;
        private String category;
        private Date expirationDate;

        public IngredientCategory getCategoryEnum() {
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
                    return IngredientCategory.REFRIGERATED;
            }
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Schema(name = "RefrigeratorResponse")
    public static class Response {
        private Integer userId;
        private Integer id;
        private String name;
        private String instruction;
        private String category;
        private Date expirationDate;

        public Response(UserIngredient userIngredient) {
            this.userId = userIngredient.getUser().getUserId();
            this.id = userIngredient.getUserIngredientId();
            this.name = userIngredient.getIngredient().getName();
            this.instruction = userIngredient.getInstructions();
            this.category = getCategoryString(userIngredient.getIngredient().getCategory());
            this.expirationDate = userIngredient.getExpirationDate();
        }

        private String getCategoryString(IngredientCategory category) {
            switch (category) {
                case FROZEN:
                    return "냉동";
                case REFRIGERATED:
                    return "냉장";
                case AMBIENT:
                    return "실온";
                case SEASONING:
                    return "조미료";
                default:
                    return "냉장";
            }
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Schema(name = "IngredientListResponse")
    public static class IngredientListResponse {
        private Integer userId;
        private Integer targetUserId;
        private Integer ingredientsCount;
        private List<IngredientInfo> ingredients;

        @Getter
        @Setter
        @AllArgsConstructor
        public static class IngredientInfo {
            private Integer id;
            private String name;
            private String category;
            private String instruction;
            private Date expirationDate;

            public IngredientInfo(UserIngredient userIngredient) {
                this.id = userIngredient.getUserIngredientId();
                this.name = userIngredient.getIngredient().getName();
                this.category = getCategoryString(userIngredient.getIngredient().getCategory());
                this.instruction = userIngredient.getInstructions();
                this.expirationDate = userIngredient.getExpirationDate();
            }

            private String getCategoryString(IngredientCategory category) {
                switch (category) {
                    case FROZEN:
                        return "냉동";
                    case REFRIGERATED:
                        return "냉장";
                    case AMBIENT:
                        return "실온";
                    case SEASONING:
                        return "조미료";
                    default:
                        return "냉장";
                }
            }
        }
    }
} 