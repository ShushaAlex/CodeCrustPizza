package org.telran.codecrustpizza.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.telran.codecrustpizza.testData.UserServiceTestData.ADDRESS_CREATE_DTO_1;
import static org.telran.codecrustpizza.testData.UserServiceTestData.CHANGE_PASSWORD_DTO_1;
import static org.telran.codecrustpizza.testData.UserServiceTestData.EMAIL_1;
import static org.telran.codecrustpizza.testData.UserServiceTestData.NAME_1;
import static org.telran.codecrustpizza.testData.UserServiceTestData.PHONE_1;
import static org.telran.codecrustpizza.testData.UserServiceTestData.PHONE_CREATE_DTO_1;
import static org.telran.codecrustpizza.testData.UserServiceTestData.USER_1;
import static org.telran.codecrustpizza.testData.UserServiceTestData.USER_CREATE_DTO_1;
import static org.telran.codecrustpizza.testData.UserServiceTestData.USER_RESPONSE_DTO_1;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = UserServiceImpl.class)
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
        UserResponseDto responseDto = USER_RESPONSE_DTO_1;

        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toResponseDto(any(User.class))).thenReturn(responseDto);
        // Execute
        List<UserResponseDto> result = userServiceImpl.findAll();
        // Validate
        assertEquals(users.size(), result.size());
        users.forEach(user -> verify(userMapper, times(3)).toResponseDto(user));
    }

    @Test
    public void findUserByIdSuccessTest() {
        // Prepare data
        User user = USER_1;
        Long userId = 1L;
        UserResponseDto responseDto = USER_RESPONSE_DTO_1;

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toResponseDto(user)).thenReturn(responseDto);
        // Execute
        UserResponseDto result = userServiceImpl.getUserDtoById(userId);
        // Validate
        assertEquals(responseDto.name(), result.name());
        assertEquals(responseDto.email(), result.email());
        verify(userRepository, times(1)).findById(userId);
        verify(userMapper, times(1)).toResponseDto(user);
    }

    @Test
    public void getUserDtoByIdThrowsEntityExceptionTest() {
        // Prepare data
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        // Validate
        assertThrows(EntityException.class, () -> userServiceImpl.getUserDtoById(userId));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void findUserByEmailSuccessTest() {
        // Prepare data
        User user = USER_1;
        String email = EMAIL_1;
        UserResponseDto responseDto = USER_RESPONSE_DTO_1;

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(userMapper.toResponseDto(user)).thenReturn(responseDto);
        // Execute
        UserResponseDto result = userServiceImpl.getUserDtoByEmail(email);
        // Validate
        assertEquals(responseDto.name(), result.name());
        assertEquals(responseDto.email(), result.email());
        verify(userRepository, times(1)).findByEmail(email);
        verify(userMapper, times(1)).toResponseDto(user);
    }

    @Test
    public void getUserDtoByEmailThrowsEntityExceptionTest() {
        // Prepare data
        String email = EMAIL_1;

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        // Validate
        assertThrows(EntityException.class, () -> userServiceImpl.getUserDtoByEmail(email));
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    public void saveUserSuccessTest() {
        // Prepare data
        User user = USER_1;
        String email = EMAIL_1;
        UserCreateRequestDto createRequestDto = USER_CREATE_DTO_1;
        UserResponseDto responseDto = USER_RESPONSE_DTO_1;

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(userMapper.toUser(createRequestDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toResponseDto(user)).thenReturn(responseDto);
        // Execute
        UserResponseDto result = userServiceImpl.save(createRequestDto);
        // Validate
        assertEquals(responseDto.email(), result.email());
        verify(userRepository, times(1)).save(user);
        verify(userRepository, times(1)).findByEmail(email);
        verify(userMapper, times(1)).toUser(createRequestDto);
        verify(userMapper, times(1)).toResponseDto(user);
    }

    @Test
    public void saveUserThrowsEntityExceptionTest() {
        // Prepare data
        String email = EMAIL_1;
        UserCreateRequestDto createRequestDto = USER_CREATE_DTO_1;

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(new User()));
        // Validate
        assertThrows(EntityException.class, () -> userServiceImpl.save(createRequestDto));
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void assignRoleSuccessTest() {
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
    public void assignRoleThrowsEntityExceptionTest() {
        // Prepare data
        Long userId = 1L;
        Role role = Role.ADMIN;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        // Validate
        assertThrows(EntityException.class, () -> userServiceImpl.assignRole(userId, role));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void addPhoneSuccessTest() {
        // Prepare data
        Long userId = 1L;
        PhoneCreateRequestDto phoneDto = PHONE_CREATE_DTO_1;
        UserResponseDto responseDto = USER_RESPONSE_DTO_1;
        User user = new User();
        Phone phone = PHONE_1;

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(phoneMapper.toPhone(phoneDto)).thenReturn(phone);
        when(phoneRepository.findByCountryCodeAndNumber(phone.getCountryCode(), phone.getNumber())).thenReturn(Optional.empty());
        when(phoneRepository.save(phone)).thenReturn(phone);
        when(userMapper.toResponseDto(user)).thenReturn(responseDto);
        // Execute
        UserResponseDto result = userServiceImpl.addPhone(userId, phoneDto);
        // Validate
        assertEquals(responseDto.email(), result.email());
        assertEquals(1, user.getPhones().size());
        verify(userRepository, times(1)).findById(userId);
        verify(phoneMapper, times(1)).toPhone(phoneDto);
        verify(phoneRepository, times(1)).findByCountryCodeAndNumber(phone.getCountryCode(), phone.getNumber());
        verify(phoneRepository, times(1)).save(phone);
        verify(userMapper, times(1)).toResponseDto(user);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void addPhoneUserNotExistThrowsEntityExceptionTest() {
        // Prepare data
        Long userId = 1L;
        PhoneCreateRequestDto phoneDto = PHONE_CREATE_DTO_1;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        // Validate
        assertThrows(EntityException.class, () -> userServiceImpl.addPhone(userId, phoneDto));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void addPhonePhoneAlreadyExistThrowsEntityExceptionTest() {
        // Prepare data
        Long userId = 1L;
        PhoneCreateRequestDto phoneDto = PHONE_CREATE_DTO_1;
        Phone phone = PHONE_1;
        User user = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(phoneMapper.toPhone(phoneDto)).thenReturn(phone);
        when(phoneRepository.findByCountryCodeAndNumber(phone.getCountryCode(), phone.getNumber())).thenReturn(Optional.of(phone));
        // Validate
        assertThrows(EntityException.class, () -> userServiceImpl.addPhone(userId, phoneDto));
        verify(userRepository, times(1)).findById(userId);
        verify(phoneMapper, times(1)).toPhone(phoneDto);
        verify(phoneRepository, times(1)).findByCountryCodeAndNumber(phone.getCountryCode(), phone.getNumber());
    }

    @Test
    void removePhoneSuccessTest() {
        // Prepare data
        Long userId = 1L;
        Long phoneId = 1L;
        User user = new User();
        Phone phone = new Phone();
        user.addPhone(phone);
        UserResponseDto responseDto = USER_RESPONSE_DTO_1;

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(phoneRepository.findById(phoneId)).thenReturn(Optional.of(phone));
        when(userMapper.toResponseDto(user)).thenReturn(responseDto);
        // Execute
        UserResponseDto result = userServiceImpl.removePhone(userId, phoneId);
        // Validate
        assertEquals(responseDto, result);
        assertEquals(0, user.getPhones().size());
        verify(userRepository, times(1)).findById(userId);
        verify(phoneRepository, times(1)).findById(phoneId);
        verify(phoneRepository, times(1)).delete(phone);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void removePhoneUserNotExistThrowsEntityExceptionTest() {
        // Prepare data
        Long userId = 1L;
        Long phoneId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        // Validate
        assertThrows(EntityException.class, () -> userServiceImpl.removePhone(userId, phoneId));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void removePhonePhoneNotExistThrowsEntityExceptionTest() {
        // Prepare data
        Long userId = 1L;
        Long phoneId = 1L;
        User user = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(phoneRepository.findById(phoneId)).thenReturn(Optional.empty());
        // Validate
        assertThrows(EntityException.class, () -> userServiceImpl.removePhone(userId, phoneId));
        verify(userRepository, times(1)).findById(userId);
        verify(phoneRepository, times(1)).findById(phoneId);
    }

    @Test
    void addAddressTest() {
        // Prepare data
        Long userId = 1L;
        AddressCreateRequestDto addressDto = ADDRESS_CREATE_DTO_1;
        UserResponseDto responseDto = USER_RESPONSE_DTO_1;
        User user = new User();
        Address address = new Address();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(addressMapper.toAddress(addressDto)).thenReturn(address);
        when(addressRepository.save(address)).thenReturn(address);
        when(userMapper.toResponseDto(user)).thenReturn(responseDto);
        // Execute
        UserResponseDto result = userServiceImpl.addAddress(userId, addressDto);
        // Validate
        assertEquals(responseDto, result);
        assertEquals(1, user.getAddresses().size());
        verify(userRepository, times(1)).findById(userId);
        verify(addressMapper, times(1)).toAddress(addressDto);
        verify(addressRepository, times(1)).save(address);
        verify(userMapper, times(1)).toResponseDto(user);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void addAddressUserNotExistThrowsEntityExceptionTest() {
        // Prepare data
        Long userId = 1L;
        AddressCreateRequestDto addressDto = ADDRESS_CREATE_DTO_1;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        // Validate
        assertThrows(EntityException.class, () -> userServiceImpl.addAddress(userId, addressDto));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void addAddressAddressAlreadyExistThrowsEntityExceptionTest() {
        // Prepare data
        Long userId = 1L;
        AddressCreateRequestDto addressDto = ADDRESS_CREATE_DTO_1;
        Address address = new Address();
        User user = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(addressMapper.toAddress(addressDto)).thenReturn(address);
        when(addressRepository.findByCityAndStreetAndHouse(address.getCity(), address.getStreet(), address.getHouse())).thenReturn(Optional.of(address));
        // Validate
        assertThrows(EntityException.class, () -> userServiceImpl.addAddress(userId, addressDto));
        verify(userRepository, times(1)).findById(userId);
        verify(addressMapper, times(1)).toAddress(addressDto);
        verify(addressRepository, times(1)).findByCityAndStreetAndHouse(address.getCity(), address.getStreet(), address.getHouse());
    }

    @Test
    void removeAddressSuccessTest() {
        // Prepare data
        Long userId = 1L;
        Long addressId = 1L;
        User user = new User();
        Address address = new Address();
        user.addAddress(address);
        UserResponseDto responseDto = USER_RESPONSE_DTO_1;

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));
        when(userMapper.toResponseDto(user)).thenReturn(responseDto);
        // Execute
        UserResponseDto result = userServiceImpl.removeAddress(userId, addressId);
        // Validate
        assertEquals(responseDto, result);
        assertEquals(0, user.getAddresses().size());
        verify(userRepository, times(1)).findById(userId);
        verify(addressRepository, times(1)).findById(addressId);
        verify(addressRepository, times(1)).delete(address);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void removeAddressUserNotExistThrowsEntityExceptionTest() {
        // Prepare data
        Long userId = 1L;
        Long addressId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        // Validate
        assertThrows(EntityException.class, () -> userServiceImpl.removePhone(userId, addressId));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void removeAddressAddressNotExistThrowsEntityExceptionTest() {
        // Prepare data
        Long userId = 1L;
        Long addressId = 1L;
        User user = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(addressRepository.findById(addressId)).thenReturn(Optional.empty());
        // Validate
        assertThrows(EntityException.class, () -> userServiceImpl.removeAddress(userId, addressId));
        verify(userRepository, times(1)).findById(userId);
        verify(addressRepository, times(1)).findById(addressId);
    }

    @Test
    void changePasswordSuccessTest() {
        // Prepare data
        Long userId = 1L;
        User user = USER_1;
        UserChangePasswordRequestDto passwordDto = CHANGE_PASSWORD_DTO_1;
        user.setPassword(passwordDto.password());
        UserResponseDto responseDto = USER_RESPONSE_DTO_1;

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toResponseDto(user)).thenReturn(responseDto);
        // Execute
        UserResponseDto result = userServiceImpl.changePassword(userId, CHANGE_PASSWORD_DTO_1);
        // Validate
        assertEquals(passwordDto.password(), user.getPassword());
        assertEquals(responseDto, result);
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(user);
        verify(userMapper, times(1)).toResponseDto(user);
    }

    @Test
    public void changePasswordUserNotExistThrowsEntityExceptionTest() {
        // Prepare data
        Long userId = 1L;
        UserChangePasswordRequestDto passwordDto = CHANGE_PASSWORD_DTO_1;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        // Validate
        assertThrows(EntityException.class, () -> userServiceImpl.changePassword(userId, passwordDto));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void changeEmailSuccessTest() {
        // Prepare data
        Long userId = 1L;
        User user = USER_1;
        String newEmail = EMAIL_1;
        UserResponseDto responseDto = USER_RESPONSE_DTO_1;
        user.setEmail(newEmail);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toResponseDto(user)).thenReturn(responseDto);
        // Execute
        UserResponseDto result = userServiceImpl.changeEmail(userId, newEmail);
        // Validate
        assertEquals(user.getEmail(), result.email());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(user);
        verify(userMapper, times(1)).toResponseDto(user);
    }

    @Test
    public void changeEmailUserNotExistThrowsEntityExceptionTest() {
        // Prepare data
        Long userId = 1L;
        String newEmail = EMAIL_1;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        // Validate
        assertThrows(EntityException.class, () -> userServiceImpl.changeEmail(userId, newEmail));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void changeNameSuccessTest() {
        // Prepare data
        Long userId = 1L;
        User user = USER_1;
        String newName = NAME_1;
        UserResponseDto responseDto = USER_RESPONSE_DTO_1;
        user.setName(newName);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toResponseDto(user)).thenReturn(responseDto);
        // Execute
        UserResponseDto result = userServiceImpl.changeName(userId, newName);
        // Validate
        assertEquals(user.getName(), result.name());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(user);
        verify(userMapper, times(1)).toResponseDto(user);
    }

    @Test
    public void changeNameUserNotExistThrowsEntityExceptionTest() {
        // Prepare data
        Long userId = 1L;
        String newName = NAME_1;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        // Validate
        assertThrows(EntityException.class, () -> userServiceImpl.changeName(userId, newName));
        verify(userRepository, times(1)).findById(userId);
    }
}