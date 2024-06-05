package org.telran.codecrustpizza.dto.menu;

import java.math.BigDecimal;

public record MenuItemResponseDto(
        String title,
        String description,
        BigDecimal price
) {
}