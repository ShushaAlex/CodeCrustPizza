package org.telran.codecrustpizza.mapper;

import org.springframework.stereotype.Component;
import org.telran.codecrustpizza.dto.ingredient.IngredientCreateRequestDto;
import org.telran.codecrustpizza.dto.ingredient.IngredientResponseDto;
import org.telran.codecrustpizza.entity.Ingredient;

@Component
public class IngredientMapper {

    public Ingredient toIngredient(IngredientCreateRequestDto dto) {
        return Ingredient.builder()
                .title(dto.title())
                .price(dto.price())
                .calories(dto.calories())
                .build();
    }

    public IngredientResponseDto toDto(Ingredient ingredient) {
        return new IngredientResponseDto(
                ingredient.getTitle(),
                ingredient.getPrice(),
                ingredient.getCalories()
        );
    }
}
