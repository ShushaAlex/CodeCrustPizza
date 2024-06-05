package org.telran.codecrustpizza.dto.menu;

import org.telran.codecrustpizza.dto.item.ItemResponseDto;
import org.telran.codecrustpizza.dto.pizza.pizzaPattern.PizzaPatternResponseDto;

import java.util.List;

public record MenuResponseDto(
        List<PizzaPatternResponseDto> pizzaPatterns,
        List<ItemResponseDto> items
) {
}