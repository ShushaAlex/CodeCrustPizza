package org.telran.codecrustpizza.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telran.codecrustpizza.dto.delivery.DeliveryResponseDto;
import org.telran.codecrustpizza.dto.order.OrderResponseDto;
import org.telran.codecrustpizza.entity.Order;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final OrderItemMapper orderItemMapper;
    private final AddressMapper addressMapper;

    public OrderResponseDto toDto(Order order) {
        return OrderResponseDto.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .items(order.getOrderItems()
                        .stream()
                        .map(orderItemMapper::toDto)
                        .toList())
                .status(order.getOrderStatus().toString())
                .delivery(new DeliveryResponseDto(addressMapper.toDto(order.getDelivery().getAddress()),
                        order.getDelivery().getDeliveryFee()))
                .itemsPriceTotal(order.getOrderItemsTotal())
                .priceTotal(order.getTotalWithDelivery())
                .createdAt(order.getCreationDate())
                .build();
    }
}