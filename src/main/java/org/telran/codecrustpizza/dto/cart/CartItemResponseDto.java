package org.telran.codecrustpizza.dto.cart;

import org.telran.codecrustpizza.dto.item.ItemResponseDto;

import java.math.BigDecimal;

public record CartItemResponseDto(
        Long id,
        ItemResponseDto itemResponseDto,
        int quantity,
        BigDecimal price
) {
}
