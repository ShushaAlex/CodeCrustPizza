package org.telran.codecrustpizza.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.telran.codecrustpizza.entity.CartItem;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @EntityGraph(value = "CartItem.withItem", type = EntityGraph.EntityGraphType.LOAD)
    List<CartItem> getCartItemByCart_Id(Long id);

    @EntityGraph(value = "CartItem.withItem", type = EntityGraph.EntityGraphType.LOAD)
    Optional<CartItem> findByCart_IdAndItem_Id(Long cartId, Long itemId);
}