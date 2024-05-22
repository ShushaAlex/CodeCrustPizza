package org.telran.codecrustpizza.dto.pizza.pizzaPattern;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.telran.codecrustpizza.dto.pizza.PizzaPatternIngredient.PizzaPatternIngredientCreateRequestDto;

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
        @NotEmpty
        Set<PizzaPatternIngredientCreateRequestDto> patternIngredients
) {
}