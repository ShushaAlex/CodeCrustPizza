package org.telran.codecrustpizza.mapper;

import org.mapstruct.Mapper;
import org.telran.codecrustpizza.dto.phone.PhoneCreateRequestDto;
import org.telran.codecrustpizza.entity.Phone;

@Mapper(componentModel = "spring")
public interface PhoneMapper {

    Phone toPhone(PhoneCreateRequestDto createRequestDto);
}