package org.telran.codecrustpizza.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telran.codecrustpizza.dto.ingredient.IngredientCreateRequestDto;
import org.telran.codecrustpizza.dto.ingredient.IngredientResponseDto;
import org.telran.codecrustpizza.service.IngredientService;

import java.util.List;

@RestController
@RequestMapping("api/ingredient")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    @GetMapping
    List<IngredientResponseDto> getAll() {

        return ingredientService.getAll();
    }

    @GetMapping("/{id}")
    IngredientResponseDto getById(@PathVariable Long id) {

        return ingredientService.getIngredientDtoById(id);
    }

    @PostMapping
    IngredientResponseDto saveIngredient(@Valid @RequestBody IngredientCreateRequestDto createDto) {

        return ingredientService.saveIngredient(createDto);
    }

    @PutMapping("/{id}/update")
    IngredientResponseDto updateIngredient(@PathVariable Long id, @Valid @RequestBody IngredientCreateRequestDto createDto) {

        return ingredientService.updateIngredient(id, createDto);
    }
}
