package org.telran.codecrustpizza.service;

import org.telran.codecrustpizza.dto.address.AddressCreateRequestDto;
import org.telran.codecrustpizza.dto.phone.PhoneCreateRequestDto;
import org.telran.codecrustpizza.dto.user.UserChangePasswordRequestDto;
import org.telran.codecrustpizza.dto.user.UserCreateRequestDto;
import org.telran.codecrustpizza.dto.user.UserResponseDto;
import org.telran.codecrustpizza.entity.User;
import org.telran.codecrustpizza.entity.enums.Role;

import java.util.List;

public interface UserService {

    List<UserResponseDto> findAll();

    UserResponseDto getUserDtoById(Long id);

    User getById(Long id);

    UserResponseDto getUserDtoByEmail(String email);

    User getByEmail(String email);

    UserResponseDto save(UserCreateRequestDto dto);

    UserResponseDto assignRole(Long userId, Role role);

    UserResponseDto addPhone(Long userId, PhoneCreateRequestDto phoneDto);

    UserResponseDto removePhone(Long userId, Long phoneId);

    UserResponseDto addAddress(Long userId, AddressCreateRequestDto addressDto);

    UserResponseDto removeAddress(Long userId, Long addressId);

    UserResponseDto changePassword(Long userId, UserChangePasswordRequestDto changePasswordRequestDto);

    UserResponseDto changeEmail(Long userId, String newEmail);

    UserResponseDto changeName(Long userId, String name);

    Long getCurrentUserId();

    //TODO добавить методы по добавлению и удалению любимой пиццы
}