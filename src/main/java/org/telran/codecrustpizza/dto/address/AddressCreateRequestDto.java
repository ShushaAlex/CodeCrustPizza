package org.telran.codecrustpizza.dto.address;

import jakarta.validation.constraints.NotBlank;

public record AddressCreateRequestDto(
        @NotBlank
        String city,

        @NotBlank
        String street,

        @NotBlank
        String house,

        String comment
) {
}