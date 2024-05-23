package org.telran.codecrustpizza.service;

import org.telran.codecrustpizza.dto.ingredient.IngredientCreateRequestDto;
import org.telran.codecrustpizza.dto.ingredient.IngredientResponseDto;
import org.telran.codecrustpizza.entity.Ingredient;

import java.util.List;

public interface IngredientService {

    List<IngredientResponseDto> getAll();

    IngredientResponseDto getIngredientDtoById(Long id);

    Ingredient findById(Long id);

    Ingredient findByTitle(String title);

    IngredientResponseDto saveIngredient(IngredientCreateRequestDto createDto);

    IngredientResponseDto deleteIngredient(Long id);

    IngredientResponseDto updateIngredient(Long id, IngredientCreateRequestDto createDto);
}