package org.telran.codecrustpizza.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.telran.codecrustpizza.dto.order.OrderResponseDto;
import org.telran.codecrustpizza.entity.enums.OrderStatus;
import org.telran.codecrustpizza.service.OrderService;
import org.telran.codecrustpizza.service.UserService;

import java.util.List;

@RestController
@RequestMapping("api/order")
@RequiredArgsConstructor
public class OrderController {

    private final UserService userService;
    private final OrderService orderService;

    @GetMapping
    public List<OrderResponseDto> getCurrentUserOrders() {
        Long userId = userService.getCurrentUserId();

        return orderService.getOrdersByUserId(userId);
    }

    @GetMapping("/{orderId}")
    OrderResponseDto getOrderById(@PathVariable Long orderId) {

        return orderService.getOrderById(orderId);
    }

    @PostMapping("/create")
    OrderResponseDto createOrder(@RequestParam Long addressId) {
        Long userId = userService.getCurrentUserId();

        return orderService.createOrder(userId, addressId);
    }

    @PutMapping("/{orderId}/cancel")
    OrderResponseDto cancelOrder(@PathVariable Long orderId) {

        return orderService.cancelOrder(orderId);
    }

    @PutMapping("/{orderId}/updateStatus")
    OrderResponseDto updateOrderStatus(@PathVariable Long orderId, @RequestParam OrderStatus status) {

        return orderService.updateOrderStatus(orderId, status);
    }
}