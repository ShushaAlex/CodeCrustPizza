package org.telran.codecrustpizza.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.telran.codecrustpizza.dto.JwtAuthenticationResponse;
import org.telran.codecrustpizza.dto.address.AddressCreateRequestDto;
import org.telran.codecrustpizza.dto.phone.PhoneCreateRequestDto;
import org.telran.codecrustpizza.dto.user.UserChangePasswordRequestDto;
import org.telran.codecrustpizza.dto.user.UserCreateRequestDto;
import org.telran.codecrustpizza.dto.user.UserResponseDto;
import org.telran.codecrustpizza.dto.user.UserSignInRequestDto;
import org.telran.codecrustpizza.entity.enums.Role;
import org.telran.codecrustpizza.security.AuthenticationService;
import org.telran.codecrustpizza.service.UserService;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @GetMapping("/all")
    public List<UserResponseDto> getAllUsers() {

        return userService.findAll();
    }

    @PermitAll
    @PostMapping("/login")
    public JwtAuthenticationResponse login(@RequestBody UserSignInRequestDto request) {
        return authenticationService.authenticate(request);
    }

    @GetMapping
    public UserResponseDto getCurrentUser() {
        Long userId = userService.getCurrentUserId();
        return userService.getUserDtoById(userId);
    }

    @PermitAll
    @PostMapping("/register")
    public UserResponseDto registerUser(@Valid @RequestBody UserCreateRequestDto userCreateRequestDto) {

        return userService.save(userCreateRequestDto);
    }

    @PutMapping("/change-password")
    public UserResponseDto changePassword(@Valid @RequestBody UserChangePasswordRequestDto passwordRequestDto) {
        Long userId = userService.getCurrentUserId();

        return userService.changePassword(userId, passwordRequestDto);
    }

    @PutMapping("/change-email")
    public UserResponseDto changeEmail(@Email @RequestParam String email) {
        Long userId = userService.getCurrentUserId();

        return userService.changeEmail(userId, email);
    }

    @PutMapping("/change-name")
    public UserResponseDto changeName(@NotBlank @RequestParam String name) {
        Long userId = userService.getCurrentUserId();
        return userService.changeName(userId, name);
    }

    //Admin
    @PutMapping("/{id}/assign-role")
    public UserResponseDto assignRole(@PathVariable Long id, @RequestParam String role) {
        Role role1 = Role.valueOf(role.toUpperCase());

        return userService.assignRole(id, role1);
    }

    @PutMapping("/phone/add")
    public UserResponseDto addPhone(@Valid @RequestBody PhoneCreateRequestDto phoneCreateRequestDto) {
        Long userId = userService.getCurrentUserId();

        return userService.addPhone(userId, phoneCreateRequestDto);
    }

    @PutMapping("/phone/remove")
    public UserResponseDto removePhone(@RequestParam Long phoneId) {
        Long userId = userService.getCurrentUserId();

        return userService.removePhone(userId, phoneId);
    }

    @PutMapping("/address/add")
    public UserResponseDto addAddress(@Valid @RequestBody AddressCreateRequestDto addressDto) {
        Long userId = userService.getCurrentUserId();

        return userService.addAddress(userId, addressDto);
    }

    @PutMapping("/address/remove")
    public UserResponseDto removeAddress(@RequestParam Long addressId) {
        Long userId = userService.getCurrentUserId();

        return userService.removeAddress(userId, addressId);
    }
}