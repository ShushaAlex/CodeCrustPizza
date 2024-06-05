package org.telran.codecrustpizza.dto.item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.telran.codecrustpizza.entity.enums.MenuCategory;

import java.math.BigDecimal;

public record ItemCreateRequestDto(

        @NotBlank
        String title,
        @NotBlank
        String description,

        MenuCategory menuCategory,
        @Positive
        BigDecimal price
) {
}