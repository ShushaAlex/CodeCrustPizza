package org.telran.codecrustpizza.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telran.codecrustpizza.dto.cart.CartItemResponseDto;
import org.telran.codecrustpizza.entity.CartItem;

@Component
public class CartItemMapper {

    private final ItemMapper itemMapper;

    @Autowired
    public CartItemMapper(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    public CartItemResponseDto toDto(CartItem cartItem) {
        return new CartItemResponseDto(
                cartItem.getId(),
                itemMapper.toDto(cartItem.getItem()),
                cartItem.getQuantity(),
                cartItem.getPrice()
        );
    }
}