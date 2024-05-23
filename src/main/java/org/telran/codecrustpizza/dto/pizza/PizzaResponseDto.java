package org.telran.codecrustpizza.dto.pizza;

import lombok.Builder;
import org.telran.codecrustpizza.dto.pizza.PizzaPatternIngredient.PizzaIngredientResponseDto;
import org.telran.codecrustpizza.entity.enums.Dough;

import java.math.BigDecimal;
import java.util.Set;

@Builder
public record PizzaResponseDto(
        Long id,
        String title,
        Dough dough,
        String description,
        int size,
        Set<PizzaIngredientResponseDto> pizzaIngredients,
        int calories,
        BigDecimal price
) {
}