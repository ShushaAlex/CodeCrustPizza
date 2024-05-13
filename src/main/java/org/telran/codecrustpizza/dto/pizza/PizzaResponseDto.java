package org.telran.codecrustpizza.dto.pizza;

import org.telran.codecrustpizza.entity.PizzaIngredient;

import java.math.BigDecimal;
import java.util.Set;

public record PizzaResponseDto(
        Long id,
        String title,
        String description,
        int size,
        Set<PizzaIngredient> pizzaIngredients,
        int calories,
        BigDecimal price
) {
}
