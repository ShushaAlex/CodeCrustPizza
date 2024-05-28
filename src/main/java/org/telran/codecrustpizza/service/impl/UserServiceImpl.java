package org.telran.codecrustpizza.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telran.codecrustpizza.dto.address.AddressCreateRequestDto;
import org.telran.codecrustpizza.dto.phone.PhoneCreateRequestDto;
import org.telran.codecrustpizza.dto.pizza.PizzaCreateRequestDto;
import org.telran.codecrustpizza.dto.pizza.PizzaResponseDto;
import org.telran.codecrustpizza.dto.pizza.PizzaShortResponseDto;
import org.telran.codecrustpizza.dto.user.UserChangePasswordRequestDto;
import org.telran.codecrustpizza.dto.user.UserCreateRequestDto;
import org.telran.codecrustpizza.dto.user.UserResponseDto;
import org.telran.codecrustpizza.dto.user.UserWithFavoritePizzaResponseDto;
import org.telran.codecrustpizza.entity.Address;
import org.telran.codecrustpizza.entity.Phone;
import org.telran.codecrustpizza.entity.Pizza;
import org.telran.codecrustpizza.entity.PizzaIngredient;
import org.telran.codecrustpizza.entity.User;
import org.telran.codecrustpizza.entity.enums.Role;
import org.telran.codecrustpizza.exception.EntityException;
import org.telran.codecrustpizza.mapper.AddressMapper;
import org.telran.codecrustpizza.mapper.PhoneMapper;
import org.telran.codecrustpizza.mapper.PizzaMapper;
import org.telran.codecrustpizza.mapper.UserMapper;
import org.telran.codecrustpizza.repository.AddressRepository;
import org.telran.codecrustpizza.repository.PhoneRepository;
import org.telran.codecrustpizza.repository.UserRepository;
import org.telran.codecrustpizza.service.PizzaService;
import org.telran.codecrustpizza.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.telran.codecrustpizza.exception.ExceptionMessage.EMAIL_EXIST;
import static org.telran.codecrustpizza.exception.ExceptionMessage.ENTITY_EXIST;
import static org.telran.codecrustpizza.exception.ExceptionMessage.NO_SUCH_EMAIL;
import static org.telran.codecrustpizza.exception.ExceptionMessage.NO_SUCH_ID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PhoneRepository phoneRepository;
    private final AddressRepository addressRepository;
    private final PhoneMapper phoneMapper;
    private final UserMapper userMapper;
    private final AddressMapper addressMapper;
    private final PizzaMapper pizzaMapper;
    private final PizzaService<PizzaResponseDto, PizzaCreateRequestDto, PizzaIngredient, Pizza> pizzaService;

    @Override
    @Transactional
    public List<UserResponseDto> findAll() {

        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public UserResponseDto getUserDtoById(Long id) {

        return userMapper.toResponseDto(getById(id));
    }

    @Override
    public User getById(Long id) {
        return userRepository.findByIdWithFavoritePizzas(id)
                .orElseThrow(() -> new EntityException(NO_SUCH_ID.getCustomMessage("user", id)));
    }

    @Override
    public User getByIdWithFavorites(Long id) {
        return userRepository.findByIdWithFavoritePizzas(id)
                .orElseThrow(() -> new EntityException(NO_SUCH_ID.getCustomMessage("user", id)));
    }

    @Override
    public UserResponseDto getUserDtoByEmail(String email) {

        return userMapper.toResponseDto(getByEmail(email));
    }

    @Override
    public User getByEmail(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityException(NO_SUCH_EMAIL.getCustomMessage("user", email)));
    }

    @Override
    @Transactional
    public UserResponseDto save(UserCreateRequestDto dto) {

        Optional<User> existingUser = userRepository.findByEmail(dto.email());
        if (existingUser.isPresent()) {
            throw new EntityException(EMAIL_EXIST.getCustomMessage("user", dto.email()));
        }

        User user = userMapper.toUser(dto);
        user.setCreationDate(LocalDateTime.now());
        userRepository.save(user);

        return userMapper.toResponseDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto assignRole(Long userId, Role role) {
        User user = getById(userId);
        user.setRole(role);
        userRepository.save(user);

        return userMapper.toResponseDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto addPhone(Long userId, PhoneCreateRequestDto phoneDto) {
        User user = getById(userId);
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
        User user = getById(userId);
        Phone phone = getPhoneById(phoneId);
        user.removePhone(phone);
        phoneRepository.delete(phone);
        userRepository.save(user);

        return userMapper.toResponseDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto addAddress(Long userId, AddressCreateRequestDto addressDto) {
        User user = getById(userId);
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
    public UserResponseDto removeAddress(Long userId, Long addressId) {
        User user = getById(userId);
        Address address = getAddressById(addressId);
        user.removeAddress(address);
        addressRepository.delete(address);
        userRepository.save(user);

        return userMapper.toResponseDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto changePassword(Long userId, UserChangePasswordRequestDto changePasswordRequestDto) {
        User user = getById(userId);
        user.setPassword(changePasswordRequestDto.password());
        userRepository.save(user);

        return userMapper.toResponseDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto changeEmail(Long userId, String newEmail) {
        User user = getById(userId);
        user.setEmail(newEmail);
        userRepository.save(user);

        return userMapper.toResponseDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto changeName(Long userId, String name) {
        User user = getById(userId);
        user.setName(name);
        userRepository.save(user);

        return userMapper.toResponseDto(user);
    }

    private Phone getPhoneById(Long phoneId) {

        return phoneRepository.findById(phoneId)
                .orElseThrow(() -> new EntityException(NO_SUCH_ID.getCustomMessage("phone", phoneId)));
    }

    private Address getAddressById(Long addressId) {

        return addressRepository.findById(addressId)
                .orElseThrow(() -> new EntityException(NO_SUCH_ID.getCustomMessage("address", addressId)));
    }

    @Override
    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("User is not authenticated");
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            User user = getByEmail(username);
            return user.getId();
        } else {
            throw new IllegalArgumentException("The primary authentication object cannot be used to obtain the ID");
        }
    }

    @Override
    public UserWithFavoritePizzaResponseDto addFavoritePizza(Long userId, Long pizzaId) {
        User user = getByIdWithFavorites(userId);
        Pizza pizza = pizzaService.getPizzaById(pizzaId);
        user.addPizza(pizza);

        userRepository.save(user);

        Set<PizzaShortResponseDto> favorites = user.getFavoritePizzas()
                .stream()
                .map(pizzaMapper::toShortDto)
                .collect(Collectors.toSet());

        return userMapper.toResponseDtoWithPizza(user, favorites);
    }

    @Override
    public UserWithFavoritePizzaResponseDto removeFavoritePizza(Long userId, Long pizzaId) {
        User user = getByIdWithFavorites(userId);
        Pizza pizza = pizzaService.getPizzaById(pizzaId);
        user.removePizza(pizza);

        userRepository.save(user);

        Set<PizzaShortResponseDto> favorites = user.getFavoritePizzas()
                .stream()
                .map(pizzaMapper::toShortDto)
                .collect(Collectors.toSet());

        return userMapper.toResponseDtoWithPizza(user, favorites);
    }
}