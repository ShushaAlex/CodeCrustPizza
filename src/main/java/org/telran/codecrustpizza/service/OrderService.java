package org.telran.codecrustpizza.service;

import org.telran.codecrustpizza.dto.order.OrderResponseDto;
import org.telran.codecrustpizza.entity.Delivery;
import org.telran.codecrustpizza.entity.enums.OrderStatus;

import java.util.List;

/**
 * Service interface for managing orders.
 */
public interface OrderService {

    /**
     * Creates a new order for a user.
     *
     * @param userId    the ID of the user placing the order.
     * @param addressId the ID of the address for delivery.
     * @return an {@link OrderResponseDto} representing the created order.
     */
    OrderResponseDto createOrder(Long userId, Long addressId);

    /**
     * Retrieves an order by its ID.
     *
     * @param orderId the ID of the order to be retrieved.
     * @return an {@link OrderResponseDto} representing the retrieved order.
     */
    OrderResponseDto getOrderById(Long orderId);

    /**
     * Cancels an order by its ID. This method does not delete order from db, it does only change order status to CANCELED
     *
     * @param orderId the ID of the order to be canceled.
     * @return an {@link OrderResponseDto} representing the canceled order.
     */
    OrderResponseDto cancelOrder(Long orderId);

    /**
     * Retrieves a list of orders placed by a user.
     *
     * @param userId the ID of the user whose orders are to be retrieved.
     * @return a list of {@link OrderResponseDto} representing the orders placed by the user.
     */
    List<OrderResponseDto> getOrdersByUserId(Long userId);

    /**
     * Retrieves a list of all orders.
     *
     * @return a list of {@link OrderResponseDto} representing all orders.
     */
    List<OrderResponseDto> getAllOrders();

    /**
     * Updates the status of an order.
     *
     * @param orderId the ID of the order to be updated.
     * @param status the new status of the order.
     * @return an {@link OrderResponseDto} representing the updated order.
     */
    OrderResponseDto updateOrderStatus(Long orderId, OrderStatus status);

    /**
     * Creates a delivery for a specified address.
     *
     * @param addressId the ID of the address for the delivery.
     * @return the {@link Delivery} representing the created delivery.
     */
    Delivery createDelivery(Long addressId);

    //Payment processPayment(Long orderId, PaymentDetails paymentDetails);
}