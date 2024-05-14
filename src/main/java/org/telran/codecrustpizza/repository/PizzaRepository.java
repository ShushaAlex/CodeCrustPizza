package org.telran.codecrustpizza.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.telran.codecrustpizza.entity.Pizza;

import java.util.Optional;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Long> {

    @EntityGraph(value = "Pizza.withIngredients", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Pizza> findByIdWithIngredients(Long id);

    Optional<Pizza> findByTitle(String title);
}
