package org.telran.codecrustpizza.dto.address;

public record AddressResponseDto(
        String city,
        String street,
        String house,
        String comment
) {
}