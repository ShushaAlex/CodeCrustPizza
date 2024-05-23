package org.telran.codecrustpizza.dto.pizza.pizzaPattern;

import org.telran.codecrustpizza.dto.pizza.PizzaPatternIngredient.PizzaIngredientResponseDto;

import java.math.BigDecimal;
import java.util.Set;

public record PizzaPatternResponseDto(
        Long id,
        String title,
        String description,
        int size,
        String dough,
        BigDecimal price,
        int calories,
        Set<PizzaIngredientResponseDto> patternIngredients
) {
}