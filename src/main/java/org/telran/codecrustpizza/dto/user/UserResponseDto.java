package org.telran.codecrustpizza.dto.user;

import org.telran.codecrustpizza.dto.address.AddressResponseDto;
import org.telran.codecrustpizza.dto.phone.PhoneResponseDto;

import java.time.LocalDateTime;
import java.util.Set;

public record UserResponseDto(
        Long id,
        String name,
        String email,
        Set<PhoneResponseDto> phones,
        Set<AddressResponseDto> addresses,
        Boolean isBlocked,
        LocalDateTime creationDate
) {
}
