package org.telran.codecrustpizza.dto.ingredient;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record IngredientCreateRequestDto(
        @NotBlank
        String title,
        @Positive
        BigDecimal price,
        @Positive
        int calories
) {
}
