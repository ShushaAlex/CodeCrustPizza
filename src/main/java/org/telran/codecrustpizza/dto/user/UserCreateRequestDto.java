package org.telran.codecrustpizza.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserCreateRequestDto(

        @NotBlank(message = "name must not be blank")
        String name,

        @NotBlank
        @Email(message = "looks like it is not a correct email")
        String email,

        @NotNull
        UserChangePasswordRequestDto passwordRequestDto
) {
}