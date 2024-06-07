package org.telran.codecrustpizza.dto.payment;

import org.telran.codecrustpizza.dto.orderItem.OrderItemResponseDto;

import java.math.BigDecimal;
import java.util.List;

public record PaymentCreateDto(

        Long orderId,
        List<OrderItemResponseDto> items,
        String status,
        BigDecimal priceTotal

) {
}
