package org.telran.codecrustpizza.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"addressSet", "favoritePizzaSet"})
@ToString(exclude = {"addressSet", "favoritePizzaSet"})
@Table(name = "user_")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private Set<Address> addressSet;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    private boolean isBlocked;
    private Set<Pizza> favoritePizzaSet;
    private LocalDateTime creationDate;
}
