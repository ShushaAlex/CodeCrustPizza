package org.telran.codecrustpizza.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.telran.codecrustpizza.entity.PizzaPattern;

import java.util.Optional;

@Repository
public interface PizzaPatternRepository extends JpaRepository<PizzaPattern, Long> {

    @EntityGraph(value = "PizzaPattern.withIngredients", type = EntityGraph.EntityGraphType.LOAD)
    Optional<PizzaPattern> findByIdWithIngredients(Long id);
}
