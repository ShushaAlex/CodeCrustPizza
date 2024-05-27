package org.telran.codecrustpizza.dto.item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ItemCreateRequestDto(

        @NotBlank
        String title,
        @NotBlank
        String description,
        @Positive
        BigDecimal price
) {
}
