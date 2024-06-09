package org.telran.codecrustpizza.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
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

@Validated
@RestController
@RequestMapping("api/ingredient")
@RequiredArgsConstructor
@Tag(name = "Ingredient Controller", description = "Operations related to ingredient management")
public class IngredientController {

    private final IngredientService ingredientService;

    @Operation(summary = "Get all ingredients", description = "Retrieve a list of all ingredients. Requires admin privileges.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of ingredients"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    List<IngredientResponseDto> getAll() {

        return ingredientService.getAll();
    }

    @Operation(summary = "Get ingredient by ID", description = "Retrieve a specific ingredient by its ID. Requires admin privileges.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved ingredient"),
            @ApiResponse(responseCode = "400", description = "Ingredient not found"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    IngredientResponseDto getById(@PathVariable Long id) {

        return ingredientService.getIngredientDtoById(id);
    }

    @Operation(summary = "Save new ingredient", description = "Save a new ingredient. Requires admin privileges.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully saved ingredient"),
            @ApiResponse(responseCode = "400", description = "Ingredient already exists"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    IngredientResponseDto saveIngredient(@Valid @RequestBody IngredientCreateRequestDto createDto) {

        return ingredientService.saveIngredient(createDto);
    }

    @Operation(summary = "Update ingredient", description = "Update an existing ingredient by its ID. Requires admin privileges.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated ingredient"),
            @ApiResponse(responseCode = "400", description = "Ingredient not found"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PutMapping("/{id}/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    IngredientResponseDto updateIngredient(@PathVariable Long id, @Valid @RequestBody IngredientCreateRequestDto createDto) {

        return ingredientService.updateIngredient(id, createDto);
    }
}