package org.telran.codecrustpizza.dto.user;

import lombok.Builder;
import org.telran.codecrustpizza.dto.pizza.PizzaShortResponseDto;

import java.util.Set;

@Builder
public record UserWithFavoritePizzaResponseDto(
        Long id,
        String name,
        Set<PizzaShortResponseDto> favoritePizzas
) {
}