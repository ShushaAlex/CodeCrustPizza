package org.telran.codecrustpizza.mapper;

import org.springframework.stereotype.Component;
import org.telran.codecrustpizza.dto.address.AddressCreateRequestDto;
import org.telran.codecrustpizza.entity.Address;

@Component
public class AddressMapper {

    public Address toAddress(AddressCreateRequestDto createRequestDto) {
        return Address.builder()
                .city(createRequestDto.city())
                .street(createRequestDto.street())
                .house(createRequestDto.house())
                .build();
    }
}
