package org.telran.codecrustpizza.mapper;

import org.springframework.stereotype.Component;
import org.telran.codecrustpizza.dto.orderItem.OrderItemResponseDto;
import org.telran.codecrustpizza.entity.OrderItem;

@Component
public class OrderItemMapper {

    public OrderItemResponseDto toDto(OrderItem orderItem) {
        return new OrderItemResponseDto(orderItem.getId(),
                orderItem.getItem().getTitle(),
                orderItem.getQuantity());
    }
}