package org.telran.codecrustpizza.dto.ingredient;

import java.math.BigDecimal;

public record IngredientResponseDto(
        Long id,
        String title,
        BigDecimal price,
        int calories
) {
}
