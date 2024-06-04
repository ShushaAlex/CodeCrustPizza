package org.telran.codecrustpizza.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telran.codecrustpizza.dto.cart.CartItemResponseDto;
import org.telran.codecrustpizza.entity.CartItem;

@Component
@RequiredArgsConstructor
public class CartItemMapper {

    private final ItemMapper itemMapper;

    public CartItemResponseDto toDto(CartItem cartItem) {
        return new CartItemResponseDto(
                cartItem.getId(),
                itemMapper.toDto(cartItem.getItem()),
                cartItem.getQuantity(),
                cartItem.getPrice()
        );
    }
}