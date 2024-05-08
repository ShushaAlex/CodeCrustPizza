package org.telran.codecrustpizza.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.telran.codecrustpizza.dto.user.UserResponseDto;
import org.telran.codecrustpizza.entity.User;
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
import static org.telran.codecrustpizza.service.impl.TestData.USER_1;
import static org.telran.codecrustpizza.service.impl.TestData.USER_RESPONSE_DTO_1;

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
    public void findAllUsers() {
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
    public void findUserById() {
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
    }

    @Test
    public void findUserByEmail() {
        // similar to the findUserById test
    }

    @Test
    public void saveUser() {
        // Please follow similar logic with your dto - save - validate flow
    }

    // other methods follow...
}