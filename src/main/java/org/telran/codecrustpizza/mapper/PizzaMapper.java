package org.telran.codecrustpizza.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telran.codecrustpizza.dto.pizza.PizzaResponseDto;
import org.telran.codecrustpizza.entity.Pizza;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PizzaMapper {

    private final PizzaPatternIngredientMapper mapper;

    public PizzaResponseDto toDto(Pizza pizza) {
        return PizzaResponseDto.builder()
                .id(pizza.getId())
                .title(pizza.getTitle())
                .dough(pizza.getDough())
                .description(pizza.getDescription())
                .size(pizza.getSize())
                .pizzaIngredients(pizza.getPizzaIngredients()
                        .stream()
                        .map(mapper::toDto)
                        .collect(Collectors.toSet()))
                .calories(pizza.getCalories())
                .price(pizza.getPrice())
                .build();
    }
}