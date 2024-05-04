package org.telran.codecrustpizza.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = {"pizzaIngredients", "pizzaPattern", "users"})
@ToString(exclude = {"pizzaIngredients", "pizzaPattern", "users"})
public class Pizza extends Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //TODO нужно пересмотреть связь с шаблонной пиццей
    @ManyToOne(fetch = FetchType.LAZY)
    private PizzaPattern pizzaPattern;

    private int size;

    @OneToMany(mappedBy = "pizza", cascade = CascadeType.ALL)
    private Set<PizzaIngredient> pizzaIngredients = new HashSet<>();

    @ManyToMany(mappedBy = "favoritePizzas")
    private Set<User> users = new HashSet<>();

    public void addPizzaIngredient(PizzaIngredient ingredient) {
        pizzaIngredients.add(ingredient);
        ingredient.setPizza(this);
    }
    public void removePizzaIngredient(PizzaIngredient ingredient) {
        pizzaIngredients.remove(ingredient);
        ingredient.setPizza(null);
    }
}