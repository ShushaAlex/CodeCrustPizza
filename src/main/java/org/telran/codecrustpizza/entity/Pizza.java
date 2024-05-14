package org.telran.codecrustpizza.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
import lombok.experimental.SuperBuilder;
import org.telran.codecrustpizza.entity.enums.Dough;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = {"pizzaIngredients", "pizzaPattern", "users"})
@ToString(exclude = {"pizzaIngredients", "pizzaPattern", "users"})
@NamedEntityGraph(
        name = "Pizza.withIngredients",
        attributeNodes = @NamedAttributeNode("pizzaIngredients")
)
@SuperBuilder
public class Pizza extends Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //TODO нужно пересмотреть связь с шаблонной пиццей
    @ManyToOne(fetch = FetchType.LAZY)
    private PizzaPattern pizzaPattern;

    private int size;

    @Enumerated(value = EnumType.STRING)
    private Dough dough;

    @Builder.Default
    @OneToMany(mappedBy = "pizza", cascade = CascadeType.ALL)
    private Set<PizzaIngredient> pizzaIngredients = new HashSet<>();

    private int calories;

    @Builder.Default
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