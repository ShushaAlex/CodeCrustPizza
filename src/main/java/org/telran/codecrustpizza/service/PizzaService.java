package org.telran.codecrustpizza.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * This interface defines operations for managing pizza patterns.
 *
 * @param <T> The type of response DTO for pizza.
 * @param <U> The type of DTO used for creating or updating pizza.
 * @param <V> The type of ingredient used in pizza patterns.
 * @param <Y> The type of entity.
 */

public interface PizzaService<T, U, V, Y> {

    List<T> findAll();

    T createPizza(U pizzaCreateDto);

    T getPizzaDtoById(Long id);

    Y getPizzaById(Long id);

    T updatePizza(Long id, U pizzaCreateDto);

    boolean deletePizza(Long id);

    int calculateTotalCalories(Set<V> ingredients);

    BigDecimal calculateTotalPrice(Set<V> ingredients);

}