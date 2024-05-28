package org.telran.codecrustpizza.dto.pizza;

import lombok.Builder;
import org.telran.codecrustpizza.entity.enums.Dough;

@Builder
public record PizzaShortResponseDto(
        Long id,
        String title,
        Dough dough,
        String description,
        int size
) {
}
