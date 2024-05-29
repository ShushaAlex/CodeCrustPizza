package org.telran.codecrustpizza.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telran.codecrustpizza.dto.order.OrderResponseDto;
import org.telran.codecrustpizza.entity.Address;
import org.telran.codecrustpizza.entity.Cart;
import org.telran.codecrustpizza.entity.Delivery;
import org.telran.codecrustpizza.entity.Order;
import org.telran.codecrustpizza.entity.OrderItem;
import org.telran.codecrustpizza.entity.User;
import org.telran.codecrustpizza.entity.enums.OrderStatus;
import org.telran.codecrustpizza.exception.EntityException;
import org.telran.codecrustpizza.mapper.OrderItemMapper;
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
    private final OrderItemMapper orderItemMapper;
    private final AddressRepository addressRepository;

    @Override
    @Transactional
    public OrderResponseDto createOrder(Long userId, Long addressId) {

        Cart cart = cartService.getCart(userId);
        User user = userService.getById(userId);

        Order order = cart.getCartItems().stream()
                .map(orderItemMapper::cartItemToOrderItem)
                .collect(Collectors.collectingAndThen(Collectors.toSet(), orderItems -> orderMapper.toEntity(user, orderItems)));
        double totalItemPrice = 0.0;

        for (OrderItem orderItem : order.getOrderItems()) {
            order.addOrderItem(orderItem);
            totalItemPrice += ((double) orderItem.getQuantity()) * orderItem.getItem().getPrice().doubleValue();
        }

        order.setOrderItemsTotal(BigDecimal.valueOf(totalItemPrice));
        order.setDelivery(createDelivery(addressId));

        if (totalItemPrice < ORDER_PRICE_FOR_FREE_DEL.doubleValue()) {
            order.getDelivery().setDeliveryFee(DELIVERY_FEE);
            order.setTotalWithDelivery(BigDecimal.valueOf(totalItemPrice + DELIVERY_FEE.doubleValue()));
        }
        order.setTotalWithDelivery(BigDecimal.valueOf(totalItemPrice));
        order.setCreationDate(LocalDateTime.now());

        order = orderRepository.save(order);

        return orderMapper.toDto(order);
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
        }
        orderRepository.save(order);
        return orderMapper.toDto(order);
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
        order.setOrderStatus(status);
        orderRepository.save(order);

        return orderMapper.toDto(order);
    }

    @Override
    public Delivery createDelivery(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new EntityException(NO_SUCH_ID.getCustomMessage("address", addressId)));

        return Delivery.builder()
                .address(address)
                .deliveryFee(BigDecimal.valueOf(0))
                .build();
    }
}