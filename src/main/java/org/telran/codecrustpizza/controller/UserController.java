package org.telran.codecrustpizza.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping
    public List<UserResponseDto> getAllUsers() {

        return userService.findAll();
    }

    @PermitAll
    @PostMapping("/login")
    public JwtAuthenticationResponse login(@RequestBody UserSignInRequestDto request) {
        return authenticationService.authenticate(request);
    }

    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable Long id) {

        return userService.getUserDtoById(id);
    }

    @PermitAll
    @PostMapping("/register")
    public UserResponseDto registerUser(@Valid @RequestBody UserCreateRequestDto userCreateRequestDto) {

        return userService.save(userCreateRequestDto);
    }

    @PutMapping("/{id}/change-password")
    public UserResponseDto changePassword(@PathVariable Long id, @Valid @RequestBody UserChangePasswordRequestDto passwordRequestDto) {

        return userService.changePassword(id, passwordRequestDto);
    }

    @PutMapping("/{id}/change-email")
    public UserResponseDto changeEmail(@PathVariable Long id, @Email @RequestParam String email) {

        return userService.changeEmail(id, email);
    }

    @PutMapping("/{id}/change-name")
    @PreAuthorize("hasRole('ROLE_USER')")
    public UserResponseDto changeName(@PathVariable Long id, @NotBlank @RequestParam String name) {

        return userService.changeName(id, name);
    }

    @PutMapping("/{id}/assign-role")
    public UserResponseDto assignRole(@PathVariable Long id, @RequestParam String role) {
        Role role1 = Role.valueOf(role.toUpperCase());

        return userService.assignRole(id, role1);
    }

    @PutMapping("/{id}/phone/add")
    public UserResponseDto addPhone(@PathVariable Long id, @Valid @RequestBody PhoneCreateRequestDto phoneCreateRequestDto) {

        return userService.addPhone(id, phoneCreateRequestDto);
    }

    @PutMapping("/{id}/phone/remove")
    public UserResponseDto removePhone(@PathVariable Long id, @RequestParam Long phoneId) {

        return userService.removePhone(id, phoneId);
    }

    @PutMapping("/{id}/address/add")
    public UserResponseDto addAddress(@PathVariable Long id, @Valid @RequestBody AddressCreateRequestDto addressDto) {

        return userService.addAddress(id, addressDto);
    }

    @PutMapping("/{id}/address/remove")
    public UserResponseDto removeAddress(@PathVariable Long id, @RequestParam Long addressId) {

        return userService.removeAddress(id, addressId);
    }

}