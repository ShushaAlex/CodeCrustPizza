package org.telran.codecrustpizza.dto.pizza.pizzaPattern;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.telran.codecrustpizza.entity.PizzaPatternIngredient;

import java.math.BigDecimal;
import java.util.Set;

public record PizzaPatternCreateDto(

        @NotBlank
        String title,
        @NotBlank
        String description,
        @Positive
        int size,
        @Pattern(regexp = "(?i)^(THICK|THIN)$")
        String dough,
        @Positive
        BigDecimal price,
        @Positive
        int calories,
        Set<PizzaPatternIngredient> patternIngredients
) {
}