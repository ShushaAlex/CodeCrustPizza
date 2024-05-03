package org.telran.codecrustpizza.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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

@Data
@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"addresses", "favoritePizzas"})
@ToString(exclude = {"addresses", "favoritePizzas"})
@Table(name = "_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phone;

    @Builder.Default
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_address",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id"))
    private Set<Address> addresses = new HashSet<>();

    @Enumerated(value = EnumType.STRING)
    private Role role;
    private boolean isBlocked;

    @Builder.Default
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_favorite_pizza",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "pizza_id"))
    private Set<Pizza> favoritePizzas = new HashSet<>();

    private LocalDateTime creationDate;


    public void addAddress(Address address) {
        addresses.add(address);
        address.getUsers().add(this);
    }

    public void removeAddress(Address address) {
        addresses.remove(address);
        address.getUsers().remove(this);
    }

    public void addPizza(Pizza pizza) {
        favoritePizzas.add(pizza);
        pizza.getUsers().add(this);
    }

    public void removePizza(Pizza pizza) {
        favoritePizzas.remove(pizza);
        pizza.getUsers().remove(this);
    }

}
