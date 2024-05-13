package org.telran.codecrustpizza.mapper;

import org.springframework.stereotype.Component;
import org.telran.codecrustpizza.dto.pizza.PizzaResponseDto;
import org.telran.codecrustpizza.entity.Pizza;

@Component
public class PizzaMapper {

    PizzaResponseDto toDto(Pizza pizza) {
        return PizzaResponseDto.builder()
                .id(pizza.getId())
                .title(pizza.getTitle())
                .dough(pizza.getPizzaPattern().getDough())
                .description(pizza.getDescription())
                .size(pizza.getSize())
                .pizzaIngredients(pizza.getPizzaIngredients())
                .calories(pizza.getCalories())
                .price(pizza.getPrice())
                .build();
    }
}
