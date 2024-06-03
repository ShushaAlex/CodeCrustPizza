package org.telran.codecrustpizza.dto.order;

import lombok.Builder;
import org.telran.codecrustpizza.dto.delivery.DeliveryResponseDto;
import org.telran.codecrustpizza.entity.OrderItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record OrderResponseDto(
        Long id,
        Long userId,
        Set<OrderItem> items,
        String status,
        BigDecimal itemsPriceTotal,
        BigDecimal itemsTotal,
        DeliveryResponseDto delivery,
        LocalDateTime createdAt
) {
}