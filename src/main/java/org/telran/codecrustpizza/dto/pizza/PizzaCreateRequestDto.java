package org.telran.codecrustpizza.dto.pizza;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record PizzaCreateRequestDto(
        @Positive
        Long patternId,
        @Positive
        int size,
        @NotBlank
        String title
) {
}
