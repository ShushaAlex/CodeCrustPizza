package org.telran.codecrustpizza.dto.pizza.PizzaPatternIngredient;

import lombok.Builder;

@Builder
public record PizzaIngredientResponseDto(
        Long id,
        String ingredientTitle,
        int quantity
) {
}