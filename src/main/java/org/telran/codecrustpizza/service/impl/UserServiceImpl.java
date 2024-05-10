package org.telran.codecrustpizza.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telran.codecrustpizza.dto.address.AddressCreateRequestDto;
import org.telran.codecrustpizza.dto.phone.PhoneCreateRequestDto;
import org.telran.codecrustpizza.dto.user.UserChangePasswordRequestDto;
import org.telran.codecrustpizza.dto.user.UserCreateRequestDto;
import org.telran.codecrustpizza.dto.user.UserResponseDto;
import org.telran.codecrustpizza.entity.Address;
import org.telran.codecrustpizza.entity.Phone;
import org.telran.codecrustpizza.entity.User;
import org.telran.codecrustpizza.entity.enums.Role;
import org.telran.codecrustpizza.exception.EntityException;
import org.telran.codecrustpizza.mapper.AddressMapper;
import org.telran.codecrustpizza.mapper.PhoneMapper;
import org.telran.codecrustpizza.mapper.UserMapper;
import org.telran.codecrustpizza.repository.AddressRepository;
import org.telran.codecrustpizza.repository.PhoneRepository;
import org.telran.codecrustpizza.repository.UserRepository;
import org.telran.codecrustpizza.service.UserService;

import java.util.List;
import java.util.Optional;

import static org.telran.codecrustpizza.exception.ExceptionMessage.EMAIL_EXIST;
import static org.telran.codecrustpizza.exception.ExceptionMessage.ENTITY_EXIST;
import static org.telran.codecrustpizza.exception.ExceptionMessage.NO_SUCH_EMAIL;
import static org.telran.codecrustpizza.exception.ExceptionMessage.NO_SUCH_ID;

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
        User user = userRepository.findById(id).orElseThrow(() -> new EntityException(NO_SUCH_ID.getCustomMessage("user", id)));

        return userMapper.toResponseDto(user);
    }

    @Override
    public UserResponseDto findByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EntityException(NO_SUCH_EMAIL.getCustomMessage("user", email)));

        return userMapper.toResponseDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto save(UserCreateRequestDto dto) {

        Optional<User> existingUser = userRepository.findByEmail(dto.email());
        if (existingUser.isPresent()) {
            throw new EntityException(EMAIL_EXIST.getCustomMessage("user", dto.email()));
        }

        User user = userMapper.toUser(dto);
        userRepository.save(user);

        return userMapper.toResponseDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto assignRole(Long userId, Role role) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityException(NO_SUCH_ID.getCustomMessage("user", userId)));
        user.setRole(role);
        userRepository.save(user);

        return userMapper.toResponseDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto addPhone(Long userId, PhoneCreateRequestDto phoneDto) {  //TODO может нужно возвращать сущность с подтянутыми телефонами
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityException(NO_SUCH_ID.getCustomMessage("user", userId)));
        Phone phone = phoneMapper.toPhone(phoneDto);

        Optional<Phone> existingPhone = phoneRepository.findByCountryCodeAndNumber(phone.getCountryCode(), phone.getNumber());
        if (existingPhone.isPresent()) {
            throw new EntityException(ENTITY_EXIST.getCustomMessage("phone"));
        }

        phone = phoneRepository.save(phone);
        user.addPhone(phone);
        userRepository.save(user);

        return userMapper.toResponseDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto removePhone(Long userId, Long phoneId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityException(NO_SUCH_ID.getCustomMessage("user", userId)));
        Phone phone = phoneRepository.findById(phoneId).orElseThrow(() -> new EntityException(NO_SUCH_ID.getCustomMessage("phone", phoneId)));
        user.removePhone(phone);
        phoneRepository.delete(phone);
        userRepository.save(user);

        return userMapper.toResponseDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto addAddress(Long userId, AddressCreateRequestDto addressDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityException(NO_SUCH_ID.getCustomMessage("user", userId)));
        Address address = addressMapper.toAddress(addressDto);

        Optional<Address> existingAddress = addressRepository.findByCityAndStreetAndHouse(address.getCity(), address.getStreet(), address.getHouse());
        if (existingAddress.isPresent()) {
            throw new EntityException(ENTITY_EXIST.getCustomMessage("address"));
        }

        address = addressRepository.save(address);
        user.addAddress(address);
        userRepository.save(user);

        return userMapper.toResponseDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto removeAddress(Long userId, Long addressId) { //TODO может нужно возвращать сущность с подтянутыми адресами
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityException(NO_SUCH_ID.getCustomMessage("user", userId)));
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new EntityException(NO_SUCH_ID.getCustomMessage("address", addressId)));
        user.removeAddress(address);
        addressRepository.delete(address);
        userRepository.save(user);

        return userMapper.toResponseDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto changePassword(Long userId, UserChangePasswordRequestDto changePasswordRequestDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityException(NO_SUCH_ID.getCustomMessage("user", userId)));
        user.setPassword(changePasswordRequestDto.password());
        userRepository.save(user);

        return userMapper.toResponseDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto changeEmail(Long userId, String newEmail) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityException(NO_SUCH_ID.getCustomMessage("user", userId)));
        user.setEmail(newEmail);
        userRepository.save(user);

        return userMapper.toResponseDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto changeName(Long userId, String name) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityException(NO_SUCH_ID.getCustomMessage("user", userId)));
        user.setName(name);
        userRepository.save(user);

        return userMapper.toResponseDto(user);
    }
}