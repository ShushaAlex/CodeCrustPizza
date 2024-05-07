package org.telran.codecrustpizza.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telran.codecrustpizza.dto.address.AddressCreateRequestDto;
import org.telran.codecrustpizza.dto.phone.PhoneCreateRequestDto;
import org.telran.codecrustpizza.dto.user.UserCreateRequestDto;
import org.telran.codecrustpizza.dto.user.UserResponseDto;
import org.telran.codecrustpizza.entity.Address;
import org.telran.codecrustpizza.entity.Phone;
import org.telran.codecrustpizza.entity.User;
import org.telran.codecrustpizza.entity.enums.Role;
import org.telran.codecrustpizza.mapper.AddressMapper;
import org.telran.codecrustpizza.mapper.PhoneMapper;
import org.telran.codecrustpizza.mapper.UserMapper;
import org.telran.codecrustpizza.repository.AddressRepository;
import org.telran.codecrustpizza.repository.PhoneRepository;
import org.telran.codecrustpizza.repository.UserRepository;
import org.telran.codecrustpizza.service.UserService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PhoneRepository phoneRepository;
    private final AddressRepository addressRepository;
    private final PhoneMapper phoneMapper;
    private final UserMapper userMapper;
    private final AddressMapper addressMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PhoneRepository phoneRepository, AddressRepository addressRepository, PhoneMapper phoneMapper, UserMapper userMapper, AddressMapper addressMapper) {
        this.userRepository = userRepository;
        this.phoneRepository = phoneRepository;
        this.addressRepository = addressRepository;
        this.phoneMapper = phoneMapper;
        this.userMapper = userMapper;
        this.addressMapper = addressMapper;
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
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("user not found")); //TODO создать свое исключение
        user.setRole(role);
        userRepository.save(user);

        return userMapper.toResponseDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto addPhone(Long userId, PhoneCreateRequestDto phoneDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("user not found")); //TODO создать свое исключение
        Phone phone = phoneMapper.toPhone(phoneDto);
        phone = phoneRepository.save(phone);
        user.addPhone(phone);

        return userMapper.toResponseDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto removePhone(Long userId, Long phoneId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("such user doesn't exist")); //TODO создать свое исключение
        Phone phone = phoneRepository.findById(phoneId).orElseThrow(() -> new NoSuchElementException("phone with such id doesn't exist")); //TODO создать свое исключение
        user.removePhone(phone);
        phoneRepository.delete(phone);
        userRepository.save(user);

        return userMapper.toResponseDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto addAddress(Long userId, AddressCreateRequestDto addressDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("user not found")); //TODO создать свое исключение
        Address address = addressMapper.toAddress(addressDto);
        address = addressRepository.save(address);
        user.addAddress(address);
        userRepository.save(user);

        return userMapper.toResponseDto(user);
    }
}
