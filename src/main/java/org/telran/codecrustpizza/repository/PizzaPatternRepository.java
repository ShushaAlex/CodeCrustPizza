package org.telran.codecrustpizza.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.telran.codecrustpizza.entity.PizzaPattern;
import org.telran.codecrustpizza.entity.enums.Dough;

import java.util.Optional;

@Repository
public interface PizzaPatternRepository extends JpaRepository<PizzaPattern, Long> {

    @EntityGraph(value = "PizzaPattern.patternIngredients", type = EntityGraph.EntityGraphType.LOAD)
    Optional<PizzaPattern> findById(Long id);

    Optional<PizzaPattern> findByTitleAndDough(String title, Dough dough);
}
