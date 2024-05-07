package org.telran.codecrustpizza.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telran.codecrustpizza.dto.address.AddressCreateRequestDto;
import org.telran.codecrustpizza.dto.phone.PhoneCreateRequestDto;
import org.telran.codecrustpizza.dto.user.UserCreateRequestDto;
import org.telran.codecrustpizza.dto.user.UserResponseDto;
import org.telran.codecrustpizza.entity.User;
import org.telran.codecrustpizza.entity.enums.Role;
import org.telran.codecrustpizza.mapper.UserMapper;
import org.telran.codecrustpizza.repository.UserRepository;
import org.telran.codecrustpizza.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserResponseDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponseDto)
                .toList();
    }

    @Override
    public UserResponseDto findById(Long id) {
        User user = userRepository.findById(id).orElse(null); // TODO бросать исключение
        return userMapper.toResponseDto(user);
    }

    @Override
    public UserResponseDto findByEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null); // TODO бросать исключение
        return userMapper.toResponseDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto save(UserCreateRequestDto dto) {
        User user = userMapper.toUser(dto);
        userRepository.save(user);
        return userMapper.toResponseDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto assignRole(Long userId, Role role) {
        return null;
    }

    @Override
    @Transactional
    public UserResponseDto addPhone(Long userId, PhoneCreateRequestDto phoneDto) {
        return null;
    }

    @Override
    public UserResponseDto addAddress(Long userId, AddressCreateRequestDto addressDto) {
        return null;
    }
}
