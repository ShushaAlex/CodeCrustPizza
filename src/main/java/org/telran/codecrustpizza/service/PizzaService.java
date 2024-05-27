package org.telran.codecrustpizza.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * This interface defines operations for managing pizza or pizza patterns.
 *
 * @param <T> The type of response DTO for pizza or pizza pattern.
 * @param <U> The type of DTO used for creating or updating pizza or pizza pattern.
 * @param <V> The type of ingredient used in pizza or pizza pattern.
 * @param <Y> The type of entity.
 */

public interface PizzaService<T, U, V, Y> {

    /**
     * Retrieves a list of all pizza response DTOs.
     *
     * @return a list of {@link T} representing all pizzas.
     */
    List<T> findAll();

    /**
     * Creates a new pizza using the provided create DTO.
     *
     * @param pizzaCreateDto the DTO containing information for creating the pizza.
     * @return a {@link T} representing the created pizza.
     */
    T createPizza(U pizzaCreateDto);

    /**
     * Retrieves a pizza response DTO by its ID.
     *
     * @param id the ID of the pizza to be retrieved.
     * @return a {@link T} representing the pizza.
     */
    T getPizzaDtoById(Long id);

    /**
     * Retrieves a pizza entity by its ID.
     *
     * @param id the ID of the pizza to be retrieved.
     * @return a {@link Y} representing the pizza entity.
     */
    Y getPizzaById(Long id);

    /**
     * Updates an existing pizza using the provided create DTO.
     *
     * @param id             the ID of the pizza to be updated.
     * @param pizzaCreateDto the DTO containing updated information for the pizza.
     * @return a {@link T} representing the updated pizza.
     */
    T updatePizza(Long id, U pizzaCreateDto);

    /**
     * Deletes a pizza by its ID.
     *
     * @param id the ID of the pizza to be deleted.
     * @return a boolean indicating whether the pizza was successfully deleted.
     */
    boolean deletePizza(Long id);

    /**
     * Calculates the total calories of a set of ingredients.
     *
     * @param ingredients the set of ingredients whose total calories are to be calculated.
     * @return the total calories of the ingredients.
     */
    int calculateTotalCalories(Set<V> ingredients);

    /**
     * Calculates the total price of a set of ingredients.
     *
     * @param ingredients the set of ingredients whose total price is to be calculated.
     * @return the total price of the ingredients.
     */
    BigDecimal calculateTotalPrice(Set<V> ingredients);
}