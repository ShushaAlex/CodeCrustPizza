package org.telran.codecrustpizza.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.telran.codecrustpizza.entity.enums.MenuCategory;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@SuperBuilder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"categories", "cartItems", "orderItems"})
@ToString(exclude = {"categories", "cartItems", "orderItems"})
@Inheritance(strategy = InheritanceType.JOINED)
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private BigDecimal price;

    @Enumerated(value = EnumType.STRING)
    private MenuCategory menuCategory;

    @Builder.Default
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "item_category",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private Set<CartItem> cartItems = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private Set<OrderItem> orderItems = new HashSet<>();

    public void addCategory(Category category) {
        categories.add(category);
        category.getItems().add(this);
    }
    public void removeCategory(Category category) {
        categories.remove(category);
        category.getItems().remove(this);
    }

    public void addCartItem(CartItem cartItem) {
        cartItems.add(cartItem);
        cartItem.setItem(this);
    }
    public void removeCartItem(CartItem cartItem) {
        cartItems.remove(cartItem);
        cartItem.setItem(null);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setItem(this);
    }

    public void removeOrderItem(OrderItem orderItem) {
        orderItems.remove(orderItem);
        orderItem.setItem(null);
    }
}