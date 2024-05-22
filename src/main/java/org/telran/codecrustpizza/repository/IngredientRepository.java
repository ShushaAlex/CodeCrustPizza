package org.telran.codecrustpizza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.telran.codecrustpizza.entity.Ingredient;

import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    Optional<Ingredient> findByTitle(String title);
}
