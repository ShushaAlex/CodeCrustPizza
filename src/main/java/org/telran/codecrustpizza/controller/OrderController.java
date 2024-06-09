package org.telran.codecrustpizza.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Tag(name = "Order Controller", description = "Operations related to order management")
public class OrderController {

    private final UserService userService;
    private final OrderService orderService;

    @Operation(summary = "Get current user's orders", description = "Retrieve a list of orders for the current user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of orders")
    })
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public List<OrderResponseDto> getCurrentUserOrders() {
        Long userId = userService.getCurrentUserId();

        return orderService.getOrdersByUserId(userId);
    }

    @Operation(summary = "Get order by ID", description = "Retrieve an order by its ID. Requires admin privileges.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved order"),
            @ApiResponse(responseCode = "400", description = "Order not found"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @GetMapping("/{orderId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    OrderResponseDto getOrderById(@PathVariable Long orderId) {

        return orderService.getOrderById(orderId);
    }

    @Operation(summary = "Create an order", description = "Create a new order for the current user. Order creates from cart items.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created order"),
            @ApiResponse(responseCode = "400", description = "Cart is empty")
    })
    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    OrderResponseDto createOrder(@RequestParam Long addressId) {
        Long userId = userService.getCurrentUserId();

        return orderService.createOrder(userId, addressId);
    }

    @Operation(summary = "Cancel an order", description = "Cancel an order by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully canceled order"),
            @ApiResponse(responseCode = "400", description = "Order not found"),
            @ApiResponse(responseCode = "400", description = "Order can not be canceled")
    })
    @PutMapping("/{orderId}/cancel")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    OrderResponseDto cancelOrder(@PathVariable Long orderId) {

        return orderService.cancelOrder(orderId);
    }

    @Operation(summary = "Update order status", description = "Update the status of an order. Requires admin privileges.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated order status"),
            @ApiResponse(responseCode = "400", description = "Order not found"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PutMapping("/{orderId}/updateStatus")
    @PreAuthorize("hasAuthority('ADMIN')")
    OrderResponseDto updateOrderStatus(@PathVariable Long orderId, @RequestParam OrderStatus status) {

        return orderService.updateOrderStatus(orderId, status);
    }
}