package org.telran.codecrustpizza.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"users", "deliveries"})
@ToString(exclude = {"users", "deliveries"})
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city;
    private String street;
    private String house;
    private String comment;

    @Builder.Default
    @ManyToMany(mappedBy = "addresses", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    private Set<Delivery> deliveries = new HashSet<>();

    public void addDelivery(Delivery delivery) {
        deliveries.add(delivery);
        delivery.setAddress(this);
    }
    public void removeDelivery(Delivery delivery) {
        deliveries.remove(delivery);
        delivery.setAddress(null);
    }
}