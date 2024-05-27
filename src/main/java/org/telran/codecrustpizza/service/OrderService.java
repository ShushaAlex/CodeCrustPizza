package org.telran.codecrustpizza.service;

import org.telran.codecrustpizza.dto.order.OrderResponseDto;
import org.telran.codecrustpizza.entity.Delivery;
import org.telran.codecrustpizza.entity.enums.OrderStatus;

import java.util.List;

public interface OrderService {

    OrderResponseDto createOrder(Long userId, Long addressId);

    OrderResponseDto getOrderById(Long orderId);

    OrderResponseDto cancelOrder(Long orderId);

    List<OrderResponseDto> getOrdersByUserId(Long userId);

    List<OrderResponseDto> getAllOrders();

    OrderResponseDto updateOrderStatus(Long orderId, OrderStatus status);

    Delivery createDelivery(Long addressId);

    //Payment processPayment(Long orderId, PaymentDetails paymentDetails);
}