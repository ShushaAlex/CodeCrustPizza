package org.telran.codecrustpizza.dto.pizza.PizzaPatternIngredient;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record PizzaPatternIngredientCreateRequestDto(

        @NotBlank
        String ingredientTitle,

        @Positive
        int quantity
) {
}
