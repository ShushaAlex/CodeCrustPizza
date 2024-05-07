package org.telran.codecrustpizza.service;

import org.telran.codecrustpizza.dto.address.AddressCreateRequestDto;
import org.telran.codecrustpizza.dto.phone.PhoneCreateRequestDto;
import org.telran.codecrustpizza.dto.user.UserCreateRequestDto;
import org.telran.codecrustpizza.dto.user.UserResponseDto;
import org.telran.codecrustpizza.entity.enums.Role;

import java.util.List;

public interface UserService {

    List<UserResponseDto> findAll();

    UserResponseDto findById(Long id);

    UserResponseDto findByEmail(String email);

    UserResponseDto save(UserCreateRequestDto dto);

    UserResponseDto assignRole(Long userId, Role role);

    UserResponseDto addPhone(Long userId, PhoneCreateRequestDto phoneDto);

    UserResponseDto addAddress(Long userId, AddressCreateRequestDto addressDto);
}