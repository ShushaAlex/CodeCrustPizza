package org.telran.codecrustpizza.dto.item;

import java.math.BigDecimal;
import java.util.List;

public record ItemResponseDto(
        Long id,
        String title,
        String description,
        BigDecimal price,
        List<String> categories
) {
}
