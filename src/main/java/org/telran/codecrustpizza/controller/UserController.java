package org.telran.codecrustpizza.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
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
import org.telran.codecrustpizza.dto.user.UserWithFavoritePizzaResponseDto;
import org.telran.codecrustpizza.entity.enums.Role;
import org.telran.codecrustpizza.security.AuthenticationService;
import org.telran.codecrustpizza.service.UserService;
import org.telran.codecrustpizza.validator.UserPasswordValidator;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "Operations related to user management")
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final UserPasswordValidator userPasswordValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(userPasswordValidator);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Get all users", description = "Retrieve a list of all users. Requires admin privileges.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of users"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public List<UserResponseDto> getAllUsers() {

        return userService.findAll();
    }

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Authenticate user and return JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully authenticated user"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    public JwtAuthenticationResponse login(@RequestBody UserSignInRequestDto request) {
        return authenticationService.authenticate(request);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Get current user", description = "Retrieve details of the currently authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user details"),
            @ApiResponse(responseCode = "401", description = "User not authenticated")
    })
    public UserResponseDto getCurrentUser() {
        Long userId = userService.getCurrentUserId();

        return userService.getUserDtoById(userId);
    }

    @PostMapping("/register")
    @PermitAll
    public UserResponseDto registerUser(@Valid @RequestBody UserCreateRequestDto userCreateRequestDto) {

        return userService.save(userCreateRequestDto);
    }

    @PutMapping("/change-password")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public UserResponseDto changePassword(@Valid @RequestBody UserChangePasswordRequestDto passwordRequestDto) {
        Long userId = userService.getCurrentUserId();

        return userService.changePassword(userId, passwordRequestDto);
    }

    @PutMapping("/change-email")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public UserResponseDto changeEmail(@Email @RequestParam String email) {
        Long userId = userService.getCurrentUserId();

        return userService.changeEmail(userId, email);
    }

    @PutMapping("/change-name")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public UserResponseDto changeName(@NotBlank @RequestParam String name) {
        Long userId = userService.getCurrentUserId();
        return userService.changeName(userId, name);
    }

    @PutMapping("/{id}/assign-role")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserResponseDto assignRole(@PathVariable Long id, @RequestParam String role) {
        Role role1 = Role.valueOf(role.toUpperCase());

        return userService.assignRole(id, role1);
    }

    @PutMapping("/phone/add")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public UserResponseDto addPhone(@Valid @RequestBody PhoneCreateRequestDto phoneCreateRequestDto) {
        Long userId = userService.getCurrentUserId();

        return userService.addPhone(userId, phoneCreateRequestDto);
    }

    @PutMapping("/phone/remove")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public UserResponseDto removePhone(@RequestParam Long phoneId) {
        Long userId = userService.getCurrentUserId();

        return userService.removePhone(userId, phoneId);
    }

    @PutMapping("/address/add")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public UserResponseDto addAddress(@Valid @RequestBody AddressCreateRequestDto addressDto) {
        Long userId = userService.getCurrentUserId();

        return userService.addAddress(userId, addressDto);
    }

    @PutMapping("/address/remove")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public UserResponseDto removeAddress(@RequestParam Long addressId) {
        Long userId = userService.getCurrentUserId();

        return userService.removeAddress(userId, addressId);
    }

    @PutMapping("/favorites/add")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public UserWithFavoritePizzaResponseDto addFavoritePizzaToCurrentUser(@RequestParam Long pizzaId) {
        Long userId = userService.getCurrentUserId();

        return userService.addFavoritePizza(userId, pizzaId);
    }

    @PutMapping("/favorites/remove")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public UserWithFavoritePizzaResponseDto removeFavoritePizzaFromCurrentUser(@RequestParam Long pizzaId) {
        Long userId = userService.getCurrentUserId();

        return userService.removeFavoritePizza(userId, pizzaId);
    }
}