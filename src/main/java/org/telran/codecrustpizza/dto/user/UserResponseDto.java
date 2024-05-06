package org.telran.codecrustpizza.dto.user;

import org.telran.codecrustpizza.entity.Address;

import java.time.LocalDateTime;
import java.util.List;

public record UserResponseDto(
        Long id,
        String name,
        String email,
        String phone,
        List<Address> addresses,
        String role,
        String isBlocked,
        LocalDateTime creationDate
) {
}