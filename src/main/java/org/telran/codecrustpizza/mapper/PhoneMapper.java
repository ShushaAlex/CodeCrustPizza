package org.telran.codecrustpizza.mapper;

import org.springframework.stereotype.Component;
import org.telran.codecrustpizza.dto.phone.PhoneCreateRequestDto;
import org.telran.codecrustpizza.entity.Phone;

@Component
public class PhoneMapper {

    public Phone toPhone(PhoneCreateRequestDto createRequestDto) {
        return Phone.builder()
                .title(createRequestDto.title())
                .countryCode(createRequestDto.countryCode())
                .areaCode(createRequestDto.areaCode())
                .number(createRequestDto.number())
                .extension(createRequestDto.extension())
                .build();
    }
}