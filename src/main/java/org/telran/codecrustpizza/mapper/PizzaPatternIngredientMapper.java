package org.telran.codecrustpizza.mapper;

import org.springframework.stereotype.Component;
import org.telran.codecrustpizza.dto.pizza.PizzaPatternIngredient.PizzaPatternIngredientCreateRequestDto;
import org.telran.codecrustpizza.dto.pizza.PizzaPatternIngredient.PizzaPatternIngredientResponseDto;
import org.telran.codecrustpizza.entity.PizzaPatternIngredient;

@Component
public class PizzaPatternIngredientMapper {

    PizzaPatternIngredient toPizzaPatternIngredient(PizzaPatternIngredientCreateRequestDto dto) {
        return PizzaPatternIngredient.builder()
                .quantity(dto.quantity())
                .build();
    }

    PizzaPatternIngredientResponseDto toDto(PizzaPatternIngredient patternIngredient) {
        return PizzaPatternIngredientResponseDto.builder()
                .id(patternIngredient.getId())
                .ingredientTitle(patternIngredient.getIngredient().getTitle())
                .quantity(patternIngredient.getQuantity())
                .build();
    }
}