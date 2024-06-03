package org.telran.codecrustpizza.mapper;

import org.springframework.stereotype.Component;
import org.telran.codecrustpizza.dto.pizza.PizzaPatternIngredient.PizzaIngredientResponseDto;
import org.telran.codecrustpizza.dto.pizza.PizzaPatternIngredient.PizzaPatternIngredientCreateRequestDto;
import org.telran.codecrustpizza.entity.PizzaIngredient;
import org.telran.codecrustpizza.entity.PizzaPatternIngredient;

@Component
public class PizzaPatternIngredientMapper {

    PizzaPatternIngredient toPizzaPatternIngredient(PizzaPatternIngredientCreateRequestDto dto) {
        return PizzaPatternIngredient.builder()
                .quantity(dto.quantity())
                .build();
    }

    PizzaIngredientResponseDto toDto(PizzaPatternIngredient patternIngredient) {
        return PizzaIngredientResponseDto.builder()
                .id(patternIngredient.getId())
                .ingredientTitle(patternIngredient.getIngredient().getTitle())
                .quantity(patternIngredient.getQuantity())
                .build();
    }

    PizzaIngredientResponseDto toDto(PizzaIngredient pizzaIngredient) {
        return PizzaIngredientResponseDto.builder()
                .id(pizzaIngredient.getId())
                .ingredientTitle(pizzaIngredient.getIngredient().getTitle())
                .quantity(pizzaIngredient.getQuantity())
                .build();
    }

}