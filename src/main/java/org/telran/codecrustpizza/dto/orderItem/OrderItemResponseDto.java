package org.telran.codecrustpizza.dto.orderItem;

public record OrderItemResponseDto(
        Long id,
        String itemTitle,
        int quantity
) {
}