package org.telran.codecrustpizza.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.telran.codecrustpizza.dto.phone.PhoneCreateRequestDto;
import org.telran.codecrustpizza.dto.user.UserCreateRequestDto;
import org.telran.codecrustpizza.dto.user.UserResponseDto;
import org.telran.codecrustpizza.entity.Phone;
import org.telran.codecrustpizza.entity.User;
import org.telran.codecrustpizza.entity.enums.Role;
import org.telran.codecrustpizza.mapper.AddressMapper;
import org.telran.codecrustpizza.mapper.PhoneMapper;
import org.telran.codecrustpizza.mapper.UserMapper;
import org.telran.codecrustpizza.repository.AddressRepository;
import org.telran.codecrustpizza.repository.PhoneRepository;
import org.telran.codecrustpizza.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.telran.codecrustpizza.TestData.EMAIL_1;
import static org.telran.codecrustpizza.TestData.PHONE_CREATE_DTO_1;
import static org.telran.codecrustpizza.TestData.USER_1;
import static org.telran.codecrustpizza.TestData.USER_CREATE_DTO_1;
import static org.telran.codecrustpizza.TestData.USER_RESPONSE_DTO_1;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = UserServiceImpl.class) // to specify which class we're testing
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PhoneRepository phoneRepository;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private PhoneMapper phoneMapper;
    @Mock
    private UserMapper userMapper;
    @Mock
    private AddressMapper addressMapper;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    public void findAllUsersTest() {
        // Prepare data
        List<User> users = List.of(new User(), new User(), new User());
        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toResponseDto(any(User.class))).thenReturn(USER_RESPONSE_DTO_1);
        // Execute
        List<UserResponseDto> result = userServiceImpl.findAll();
        // Validate
        assertEquals(result.size(), users.size());
        users.forEach(user -> verify(userMapper, times(3)).toResponseDto(user));
    }

    @Test
    public void findUserByIdTest() {
        // Prepare data
        User user = USER_1;
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toResponseDto(user)).thenReturn(USER_RESPONSE_DTO_1);
        // Execute
        UserResponseDto result = userServiceImpl.findById(userId);
        // Validate
        assertEquals(result.name(), USER_RESPONSE_DTO_1.name());
        assertEquals(result.email(), USER_RESPONSE_DTO_1.email());
        verify(userRepository, times(1)).findById(userId);
        verify(userMapper, times(1)).toResponseDto(user);
    }

    @Test
    public void findUserByEmailTest() {
        // Prepare data
        User user = USER_1;
        String email = EMAIL_1;
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(userMapper.toResponseDto(user)).thenReturn(USER_RESPONSE_DTO_1);
        // Execute
        UserResponseDto result = userServiceImpl.findByEmail(email);
        // Validate
        assertEquals(result.name(), USER_RESPONSE_DTO_1.name());
        assertEquals(result.email(), USER_RESPONSE_DTO_1.email());
        verify(userRepository, times(1)).findByEmail(email);
        verify(userMapper, times(1)).toResponseDto(user);
    }

    @Test
    public void saveUserTest() {
        // Prepare data
        User user = USER_1;
        String email = EMAIL_1;
        UserCreateRequestDto createRequestDto = USER_CREATE_DTO_1;
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(userMapper.toUser(createRequestDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toResponseDto(user)).thenReturn(USER_RESPONSE_DTO_1);
        // Execute
        UserResponseDto result = userServiceImpl.save(createRequestDto);
        // Validate
        assertEquals(result.name(), USER_RESPONSE_DTO_1.name());
        assertEquals(result.email(), USER_RESPONSE_DTO_1.email());
        verify(userRepository, times(1)).save(user);
        verify(userRepository, times(1)).findByEmail(email);
        verify(userMapper, times(1)).toUser(createRequestDto);
        verify(userMapper, times(1)).toResponseDto(user);
    }

    @Test
    void assignRoleTest() {
        // Prepare data
        Long userId = 1L;
        Role role = Role.ADMIN;
        User user = new User();
        user.setRole(Role.USER);
        UserResponseDto responseDto = USER_RESPONSE_DTO_1;

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toResponseDto(user)).thenReturn(responseDto);
        // Execute
        UserResponseDto result = userServiceImpl.assignRole(userId, role);
        // Validate
        assertEquals(responseDto, result);
        assertEquals(role, user.getRole());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(user);
        verify(userMapper, times(1)).toResponseDto(user);
    }

    @Test
    void addPhone() { //TODO добелать
        // Prepare data
        Long userId = 1L;
        PhoneCreateRequestDto phoneDto = PHONE_CREATE_DTO_1;
        User user = new User();
        user.setId(userId);
        Phone phone = new Phone();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(phoneMapper.toPhone(phoneDto)).thenReturn(phone);
        when(phoneRepository.save(phone)).thenReturn(phone);
        when(userMapper.toResponseDto(user)).thenReturn(USER_RESPONSE_DTO_1);
        // Execute
        UserResponseDto result = userServiceImpl.addPhone(userId, phoneDto);
        // Validate
        assertEquals(result.name(), USER_RESPONSE_DTO_1.name());
        assertEquals(result.email(), USER_RESPONSE_DTO_1.email());
        verify(userRepository, times(1)).findById(userId);
        verify(phoneMapper, times(1)).toPhone(phoneDto);
        verify(phoneRepository, times(1)).save(phone);
        verify(userMapper, times(1)).toResponseDto(user);
    }

    @Test
    void removePhone() {
    }

    @Test
    void addAddress() {
    }

    @Test
    void removeAddress() {
    }

    @Test
    void changePassword() {
    }

    @Test
    void changeEmail() {
    }

    @Test
    void changeName() {
    }


    // other methods follow...
}