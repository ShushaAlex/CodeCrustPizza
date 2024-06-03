package org.telran.codecrustpizza.dto.cart;

import java.util.List;

public record CartResponseDto(
        Long userId,
        List<CartItemResponseDto> cartItems
) {
}
