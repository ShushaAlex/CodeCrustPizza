package org.telran.codecrustpizza.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telran.codecrustpizza.dto.pizza.PizzaPatternIngredient.PizzaPatternIngredientResponseDto;
import org.telran.codecrustpizza.dto.pizza.pizzaPattern.PizzaPatternCreateDto;
import org.telran.codecrustpizza.dto.pizza.pizzaPattern.PizzaPatternResponseDto;
import org.telran.codecrustpizza.entity.PizzaPattern;
import org.telran.codecrustpizza.entity.PizzaPatternIngredient;
import org.telran.codecrustpizza.entity.enums.Dough;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PizzaPatternMapper {

    private final PizzaPatternIngredientMapper pizzaPatternIngredientMapper;

    public PizzaPattern toPizzaPattern(PizzaPatternCreateDto dto) {

        Set<PizzaPatternIngredient> ppIngredients = dto.patternIngredients()
                .stream()
                .map(pizzaPatternIngredientMapper::toPizzaPatternIngredient)
                .collect(Collectors.toSet());

        return PizzaPattern.builder()
                .title(dto.title())
                .description(dto.description())
                .size(dto.size())
                .dough(Dough.valueOf(dto.dough().toUpperCase()))
                .build();
    }

    public PizzaPatternResponseDto toDto(PizzaPattern pizzaPattern) {

        Set<PizzaPatternIngredientResponseDto> ppIngredients = pizzaPattern.getPatternIngredients()
                .stream()
                .map(pizzaPatternIngredientMapper::toDto)
                .collect(Collectors.toSet());

        return new PizzaPatternResponseDto(
                pizzaPattern.getId(),
                pizzaPattern.getTitle(),
                pizzaPattern.getDescription(),
                pizzaPattern.getSize(),
                pizzaPattern.getDough().name(),
                pizzaPattern.getPrice(),
                pizzaPattern.getCalories(),
                ppIngredients
        );
    }
}
