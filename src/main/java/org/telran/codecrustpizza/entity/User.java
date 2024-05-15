package org.telran.codecrustpizza.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedEntityGraphs;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.telran.codecrustpizza.entity.enums.Role;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.telran.codecrustpizza.util.EntityUtil.changeManyToManyRef;

@Data
@Entity
@Builder(toBuilder = true) //затем вызываем toBuilder.build()
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"phones", "addresses", "favoritePizzas", "cart", "orders"})
@ToString(exclude = {"phones", "addresses", "favoritePizzas", "cart", "orders"})
@Table(name = "_user")
@NamedEntityGraphs({ //TODO подумать над тем, какие энтити графы необходимы в каждой сущности
        @NamedEntityGraph(
                name = "User.withPhones", // как будет называться что мы получаем
                attributeNodes = @NamedAttributeNode("phones") // какое поле подтягиваем
        ),
        @NamedEntityGraph(
                name = "User.withAddresses",
                attributeNodes = @NamedAttributeNode("addresses")
        ),
        @NamedEntityGraph(
                name = "User.withPhonesAndAddresses",
                attributeNodes = {@NamedAttributeNode("phones"), @NamedAttributeNode("addresses")}
        )
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Phone> phones = new HashSet<>();

    @Builder.Default
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_address",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id"))
    private Set<Address> addresses = new HashSet<>();

    @Enumerated(value = EnumType.STRING)
    @Builder.Default
    private Role role = Role.USER;

    private boolean isBlocked;

    @Builder.Default
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_favorite_pizza",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "pizza_id"))
    private Set<Pizza> favoritePizzas = new HashSet<>();

    @OneToOne(mappedBy = "user",
            optional = false,
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Cart cart;

    private LocalDateTime creationDate;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Order> orders = new HashSet<>();

    public void addAddress(Address address) {
        addresses.add(address);
        address.getUsers().add(this);
    }
    public void removeAddress(Address address) {
        addresses.remove(address);
        address.getUsers().remove(this);
    }

    public void addPizza(Pizza pizza) {
//        favoritePizzas.add(pizza);
//        pizza.getUsers().add(this);
        changeManyToManyRef(favoritePizzas::add, pizza.getUsers()::add, pizza, this);
    }


    public void removePizza(Pizza pizza) {
        favoritePizzas.remove(pizza);
        pizza.getUsers().remove(this);
    }

    public void addOrder(Order order) {
        orders.add(order);
        order.setUser(this);
    }
    public void removeOrder(Order order) {
        orders.remove(order);
        order.setUser(null);
    }

    public void setCart(Cart cart) {
        this.cart = cart;
        cart.setUser(this);
    }

    public void addPhone(Phone phone) {
        phones.add(phone);
        phone.setUser(this);
    }

    public void removePhone(Phone phone) {
        phones.remove(phone);
        phone.setUser(null);
    }
}