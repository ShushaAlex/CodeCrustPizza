package org.telran.codecrustpizza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.telran.codecrustpizza.entity.Cart;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser_Id(Long userId);
}