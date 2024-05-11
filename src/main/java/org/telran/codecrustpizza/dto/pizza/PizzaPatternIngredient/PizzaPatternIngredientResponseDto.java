package org.telran.codecrustpizza.dto.pizza.PizzaPatternIngredient;

import lombok.Builder;

@Builder
public record PizzaPatternIngredientResponseDto(
        Long id,
        String ingredientTitle,
        int quantity
) {
}
