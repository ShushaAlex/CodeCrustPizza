package org.telran.codecrustpizza.mapper;

import org.mapstruct.Mapper;
import org.telran.codecrustpizza.dto.address.AddressCreateRequestDto;
import org.telran.codecrustpizza.entity.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Address toAddress(AddressCreateRequestDto createRequestDto);
}
