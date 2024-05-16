package org.telran.codecrustpizza.dto.item;

import java.math.BigDecimal;

public record ItemCreateRequestDto(

        String title,
        String description,
        BigDecimal price
) {
}
