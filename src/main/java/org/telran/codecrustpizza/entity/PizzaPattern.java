package org.telran.codecrustpizza.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.telran.codecrustpizza.entity.enums.Dough;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"patternIngredients", "pizzas"})
@ToString(exclude = {"patternIngredients", "pizzas"})
@NamedEntityGraph(
        name = "PizzaPattern.patternIngredients",
        attributeNodes = @NamedAttributeNode("patternIngredients")
)
public class PizzaPattern {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private int size;

    @Enumerated(value = EnumType.STRING)
    private Dough dough;

    private BigDecimal price;

    @Builder.Default
    @OneToMany(mappedBy = "pizzaPattern", cascade = CascadeType.ALL)
    private Set<PizzaPatternIngredient> patternIngredients = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "pizzaPattern", cascade = CascadeType.ALL)
    private Set<Pizza> pizzas = new HashSet<>();

    private int calories;

    public void addPizzaPatternIngredient(PizzaPatternIngredient patternIngredient) {
        patternIngredients.add(patternIngredient);
        patternIngredient.setPizzaPattern(this);
    }
    public void removePizzaPatternIngredient(PizzaPatternIngredient patternIngredient) {
        patternIngredients.remove(patternIngredient);
        patternIngredient.setPizzaPattern(null);
    }

    public void addPizza(Pizza pizza) {
        pizzas.add(pizza);
        pizza.setPizzaPattern(this);
    }
    public void removePizza(Pizza pizza) {
        pizzas.remove(pizza);
        pizza.setPizzaPattern(null);
    }
}