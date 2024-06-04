package org.telran.codecrustpizza.dto.delivery;

import org.telran.codecrustpizza.dto.address.AddressResponseDto;

import java.math.BigDecimal;

public record DeliveryResponseDto(
        AddressResponseDto address,
        BigDecimal fee
) {
}
