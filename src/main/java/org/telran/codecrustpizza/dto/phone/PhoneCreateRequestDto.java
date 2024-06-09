package org.telran.codecrustpizza.dto.phone;

import jakarta.validation.constraints.NotBlank;

public record PhoneCreateRequestDto(
        @NotBlank
        String title,
        @NotBlank
        String countryCode,
        @NotBlank
        String areaCode,
        @NotBlank
        String number,
        @NotBlank
        String extension
) {
}