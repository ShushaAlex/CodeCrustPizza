package org.telran.codecrustpizza.service;

import org.telran.codecrustpizza.dto.ingredient.IngredientCreateRequestDto;
import org.telran.codecrustpizza.dto.ingredient.IngredientResponseDto;

import java.util.List;

public interface IngredientService {

    List<IngredientResponseDto> getAll();

    IngredientResponseDto getById(Long id);

    IngredientResponseDto saveIngredient(IngredientCreateRequestDto createDto);

    IngredientResponseDto deleteIngredient(Long id);

    IngredientResponseDto updateIngredient(Long id, IngredientCreateRequestDto createDto);
}