package org.telran.codecrustpizza.dto.order;

import lombok.Builder;
import org.telran.codecrustpizza.dto.delivery.DeliveryResponseDto;
import org.telran.codecrustpizza.dto.orderItem.OrderItemResponseDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record OrderResponseDto(
        Long id,
        Long userId,
        List<OrderItemResponseDto> items,
        String status,
        BigDecimal itemsPriceTotal,
        BigDecimal priceTotal,
        DeliveryResponseDto delivery,
        LocalDateTime createdAt
) {
}