package org.telran.codecrustpizza.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode(exclude = "pizzaIngredients")
@ToString(exclude = "pizzaIngredients")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private BigDecimal price;
    private int calories;

    @Builder.Default
    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL)
    private Set<PizzaIngredient> pizzaIngredients = new HashSet<>();

    public void addPizzaIngredient(PizzaIngredient ingredient) {
        pizzaIngredients.add(ingredient);
        ingredient.setIngredient(this);
    }

    public void removePizzaIngredient(PizzaIngredient ingredient) {
        pizzaIngredients.remove(ingredient);
        ingredient.setIngredient(null);
    }
}
