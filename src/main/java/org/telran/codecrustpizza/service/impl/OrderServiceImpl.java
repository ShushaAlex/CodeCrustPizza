package org.telran.codecrustpizza.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telran.codecrustpizza.dto.order.OrderResponseDto;
import org.telran.codecrustpizza.entity.Address;
import org.telran.codecrustpizza.entity.Cart;
import org.telran.codecrustpizza.entity.CartItem;
import org.telran.codecrustpizza.entity.Delivery;
import org.telran.codecrustpizza.entity.Order;
import org.telran.codecrustpizza.entity.OrderItem;
import org.telran.codecrustpizza.entity.enums.OrderStatus;
import org.telran.codecrustpizza.exception.CancelOrderException;
import org.telran.codecrustpizza.exception.CartIsEmptyException;
import org.telran.codecrustpizza.exception.EntityException;
import org.telran.codecrustpizza.mapper.OrderMapper;
import org.telran.codecrustpizza.repository.AddressRepository;
import org.telran.codecrustpizza.repository.OrderRepository;
import org.telran.codecrustpizza.service.CartService;
import org.telran.codecrustpizza.service.OrderService;
import org.telran.codecrustpizza.service.UserService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.telran.codecrustpizza.entity.enums.OrderStatus.CANCELED;
import static org.telran.codecrustpizza.entity.enums.OrderStatus.IN_PROCESSING;
import static org.telran.codecrustpizza.entity.enums.OrderStatus.RECEIVED;
import static org.telran.codecrustpizza.exception.ExceptionMessage.NO_SUCH_ID;
import static org.telran.codecrustpizza.util.DeliveryConstants.DELIVERY_FEE;
import static org.telran.codecrustpizza.util.DeliveryConstants.ORDER_PRICE_FOR_FREE_DEL;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CartService cartService;
    private final UserService userService;
    private final AddressRepository addressRepository;

    @Override
    @Transactional
    public OrderResponseDto createOrder(Long userId, Long addressId) {

        Cart cart = cartService.getCart(userId);

        if (cart.getCartItems().isEmpty())
            throw new CartIsEmptyException("Your cart is empty, first add items to the cart.");

        Order order = Order.builder()
                .user(userService.getById(userId))
                .orderStatus(IN_PROCESSING)
                .build();

        order.setOrderItems(cart.getCartItems().stream()
                .map(this::cartItemToOrderItem)
                .collect(Collectors.toSet()));

        double totalItemPrice = 0.0;

        for (OrderItem orderItem : order.getOrderItems()) {
            totalItemPrice += ((double) orderItem.getQuantity()) * orderItem.getItem().getPrice().doubleValue();
        }

        order.setOrderItemsTotal(BigDecimal.valueOf(totalItemPrice));
        order.setDelivery(createDelivery(addressId));

        order.setTotalWithDelivery(BigDecimal.valueOf(totalItemPrice));

        if (totalItemPrice < ORDER_PRICE_FOR_FREE_DEL.doubleValue()) {
            order.getDelivery().setDeliveryFee(DELIVERY_FEE);
            order.setTotalWithDelivery(BigDecimal.valueOf(totalItemPrice + DELIVERY_FEE.doubleValue()));
        }

        order.setCreationDate(LocalDateTime.now());

        order = orderRepository.save(order);

        cartService.clearCart(userId);

        return orderMapper.toDto(order); // ToDo fix mapping to dto and clear cart after order
    }

    @Override
    @Transactional
    public OrderResponseDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityException(NO_SUCH_ID.getCustomMessage("order", orderId)));

        return orderMapper.toDto(order);
    }

    @Override
    @Transactional
    public OrderResponseDto cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityException(NO_SUCH_ID.getCustomMessage("order", orderId)));

        if (order.getOrderStatus().equals(RECEIVED) || order.getOrderStatus().equals(IN_PROCESSING)) {
            order.setOrderStatus(CANCELED);
            orderRepository.save(order);
            return orderMapper.toDto(order);
        } else {
            throw new CancelOrderException("Order can not be canceled. It has already status: " + order.getOrderStatus());
        }
    }

    @Override
    public List<OrderResponseDto> getOrdersByUserId(Long userId) {

        return orderRepository.findByUser_Id(userId)
                .stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public List<OrderResponseDto> getAllOrders() {

        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public OrderResponseDto updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityException(NO_SUCH_ID.getCustomMessage("order", orderId)));

        if (order.getOrderStatus().equals(CANCELED)) {
            throw new CancelOrderException("this order is already canceled");
        }

        order.setOrderStatus(status);
        orderRepository.save(order);

        return orderMapper.toDto(order);
    }

    @Override
    @Transactional
    public Delivery createDelivery(Long addressId) {

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new EntityException(NO_SUCH_ID.getCustomMessage("address", addressId)));

        return Delivery.builder()
                .address(address)
                .deliveryFee(BigDecimal.valueOf(0))
                .build();
    }

    private OrderItem cartItemToOrderItem(CartItem cartItem) {
        return OrderItem.builder()
                .item(cartItem.getItem())
                .quantity(cartItem.getQuantity())
                .build();
    }
}