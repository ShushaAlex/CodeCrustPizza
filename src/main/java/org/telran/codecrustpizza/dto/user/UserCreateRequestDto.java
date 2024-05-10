package org.telran.codecrustpizza.dto.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserCreateRequestDto(

        @NotBlank
        String name,

        @NotBlank
        @Email
        String email,

        @NotNull
        @Valid
        UserChangePasswordRequestDto passwordRequestDto
) {
}
