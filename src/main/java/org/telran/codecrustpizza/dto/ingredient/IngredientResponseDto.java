package org.telran.codecrustpizza.dto.ingredient;

import java.math.BigDecimal;

public record IngredientResponseDto(
        String title,
        BigDecimal price,
        int calories
) {
}
