package org.telran.codecrustpizza.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.telran.codecrustpizza.dto.address.AddressResponseDto;
import org.telran.codecrustpizza.dto.phone.PhoneResponseDto;
import org.telran.codecrustpizza.dto.user.UserCreateRequestDto;
import org.telran.codecrustpizza.dto.user.UserResponseDto;
import org.telran.codecrustpizza.entity.User;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PhoneMapper phoneMapper;
    private final AddressMapper addressMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponseDto toResponseDto(User user) {
        Set<AddressResponseDto> addresses = user.getAddresses()
                .stream()
                .map(addressMapper::toDto)
                .collect(Collectors.toSet());

        Set<PhoneResponseDto> phones = user.getPhones()
                .stream()
                .map(phoneMapper::toDto)
                .collect(Collectors.toSet());

        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                phones,
                addresses,
                user.isBlocked(),
                user.getCreationDate()
        );
    }

    public User toUser(UserCreateRequestDto dto) {
        return User.builder()
                .name(dto.name())
                .email(dto.email())
                .password(passwordEncoder.encode(dto.passwordRequestDto().password()))
                .build();
    }
}