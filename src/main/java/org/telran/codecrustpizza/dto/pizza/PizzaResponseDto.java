package org.telran.codecrustpizza.dto.pizza;

import lombok.Builder;
import org.telran.codecrustpizza.entity.PizzaIngredient;
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
        Set<PizzaIngredient> pizzaIngredients,
        int calories,
        BigDecimal price
) {
}
