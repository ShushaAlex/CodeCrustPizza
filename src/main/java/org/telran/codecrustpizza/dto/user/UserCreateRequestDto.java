package org.telran.codecrustpizza.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserCreateRequestDto(

        @NotBlank
        String name,

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$",
                message = "Password must be between 8 and 20 uppercase and lowercase symbols, digit and special symbols '@#$%^&+='"
        )
        String password,

        @NotBlank
        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$",
                message = "Password must be between 8 and 20 uppercase and lowercase symbols, digit and special symbols '@#$%^&+='"
        )
        String confirmPassword,

        @NotBlank
        @Pattern(
                regexp = "^\\+(?:[0-9] ?){6,14}[0-9]$",
                message = "This is an incorrect phone number. Enter your phone without hyphens in such format +4910000000000"
        )
        String phone
) {
}
