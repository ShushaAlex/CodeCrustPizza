package org.telran.codecrustpizza.mapper;

import org.springframework.stereotype.Component;
import org.telran.codecrustpizza.dto.phone.PhoneResponseDto;
import org.telran.codecrustpizza.entity.Phone;

@Component
public class PhoneMapper {

    PhoneResponseDto toDto(Phone phone) {
        return new PhoneResponseDto(
                phone.getTitle(),
                phone.getCountryCode() + phone.getAreaCode() + phone.getNumber() + phone.getExtension()
        );
    }
}