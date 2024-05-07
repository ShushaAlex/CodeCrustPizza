package org.telran.codecrustpizza.dto.phone;

public record PhoneCreateRequestDto(
        String title,
        String countryCode,
        String areaCode,
        String number,
        String extension
) {
}
