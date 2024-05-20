package org.telran.codecrustpizza.service;

import org.telran.codecrustpizza.dto.cart.CartItemResponseDto;
import org.telran.codecrustpizza.dto.cart.CartResponseDto;
import org.telran.codecrustpizza.entity.Cart;

import java.util.List;

public interface CartService {

    Cart getCart(Long userId);

    CartResponseDto addItemToCart(Long itemId, Long userId);

    CartResponseDto removeItemFromCart(Long itemId, Long userId);

    List<CartItemResponseDto> getCartItems(Long userId);

    CartResponseDto clearCart(Long userId);
}