package org.telran.codecrustpizza.mapper;

import org.springframework.stereotype.Component;
import org.telran.codecrustpizza.dto.order.OrderResponseDto;
import org.telran.codecrustpizza.dto.payment.PaymentCreateDto;

@Component
public class PaymentMapper {

    public PaymentCreateDto toPaymentCreateDto(OrderResponseDto orderResponseDto) {

        return new PaymentCreateDto(
                orderResponseDto.id(),
                orderResponseDto.items(),
                orderResponseDto.status(),
                orderResponseDto.priceTotal());
    }
}