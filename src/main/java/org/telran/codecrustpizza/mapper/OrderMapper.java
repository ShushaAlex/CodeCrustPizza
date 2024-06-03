package org.telran.codecrustpizza.mapper;

import org.springframework.stereotype.Component;
import org.telran.codecrustpizza.dto.order.OrderResponseDto;
import org.telran.codecrustpizza.entity.Order;
import org.telran.codecrustpizza.entity.OrderItem;
import org.telran.codecrustpizza.entity.User;
import org.telran.codecrustpizza.entity.enums.OrderStatus;

import java.util.Set;

@Component
public class OrderMapper {

    public OrderResponseDto toDto(Order order) {
        return OrderResponseDto.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .createdAt(order.getCreationDate())
                .build();
    }

    public Order toEntity(User user, Set<OrderItem> orderItems) {
        return Order.builder()
                .user(user)
                .orderItems(orderItems)
                .orderStatus(OrderStatus.IN_PROCESSING)
                .build();
    }
}