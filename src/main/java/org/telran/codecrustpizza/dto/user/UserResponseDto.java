package org.telran.codecrustpizza.dto.user;

import org.telran.codecrustpizza.entity.Address;
import org.telran.codecrustpizza.entity.Phone;

import java.time.LocalDateTime;
import java.util.List;

public record UserResponseDto(
        Long id,
        String name,
        String email,
        List<Phone> phones,
        List<Address> addresses,
        Boolean isBlocked,
        LocalDateTime creationDate
) {
}
