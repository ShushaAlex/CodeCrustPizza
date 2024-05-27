package org.telran.codecrustpizza.service;

import org.telran.codecrustpizza.dto.ingredient.IngredientCreateRequestDto;
import org.telran.codecrustpizza.dto.ingredient.IngredientResponseDto;
import org.telran.codecrustpizza.entity.Ingredient;

import java.util.List;

/**
 * Service interface for managing ingredients for pizza.
 */
public interface IngredientService {

    /**
     * Retrieves a list of all ingredients from db.
     *
     * @return a list of {@link IngredientResponseDto} representing all ingredients.
     */
    List<IngredientResponseDto> getAll();

    /**
     * Retrieves an ingredient DTO by its ID.
     *
     * @param id the ID of the ingredient to be retrieved.
     * @return an {@link IngredientResponseDto} representing the ingredient.
     */
    IngredientResponseDto getIngredientDtoById(Long id);

    /**
     * Finds an ingredient entity by its ID.
     *
     * @param id the ID of the ingredient to be found.
     * @return the {@link Ingredient} entity.
     */
    Ingredient findById(Long id);

    /**
     * Finds an ingredient entity by its title.
     *
     * @param title the title of the ingredient to be found.
     * @return the {@link Ingredient} entity.
     */
    Ingredient findByTitle(String title);

    /**
     * Saves a new ingredient in db.
     *
     * @param createDto the DTO containing information for creating the ingredient.
     * @return an {@link IngredientResponseDto} representing the saved ingredient.
     */
    IngredientResponseDto saveIngredient(IngredientCreateRequestDto createDto);

    /**
     * Deletes an ingredient by its ID.
     *
     * @param id the ID of the ingredient to be deleted.
     * @return an {@link IngredientResponseDto} representing the deleted ingredient.
     */
    IngredientResponseDto deleteIngredient(Long id);

    /**
     * Updates an existing ingredient.
     *
     * @param id        the ID of the ingredient to be updated.
     * @param createDto the DTO containing updated information for the ingredient.
     * @return an {@link IngredientResponseDto} representing the updated ingredient.
     */
    IngredientResponseDto updateIngredient(Long id, IngredientCreateRequestDto createDto);
}