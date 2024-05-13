package org.telran.codecrustpizza.mapper;

import org.springframework.stereotype.Component;
import org.telran.codecrustpizza.dto.pizza.pizzaPattern.PizzaPatternCreateDto;
import org.telran.codecrustpizza.dto.pizza.pizzaPattern.PizzaPatternResponseDto;
import org.telran.codecrustpizza.entity.PizzaPattern;
import org.telran.codecrustpizza.entity.enums.Dough;

@Component
public class PizzaPatternMapper {

    public PizzaPattern toPizzaPattern(PizzaPatternCreateDto dto) {
        return PizzaPattern.builder()
                .title(dto.title())
                .description(dto.description())
                .size(dto.size())
                .dough(Dough.valueOf(dto.dough().toUpperCase()))
                .patternIngredients(dto.patternIngredients())
                .build();
    }

    public PizzaPatternResponseDto toDto(PizzaPattern pizzaPattern) {
        return new PizzaPatternResponseDto(
                pizzaPattern.getId(),
                pizzaPattern.getTitle(),
                pizzaPattern.getDescription(),
                pizzaPattern.getSize(),
                pizzaPattern.getDough().name(),
                pizzaPattern.getPrice(),
                pizzaPattern.getCalories(),
                pizzaPattern.getPatternIngredients()
        );
    }
}
