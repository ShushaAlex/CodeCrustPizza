package org.telran.codecrustpizza.dto.user;

import jakarta.validation.constraints.Pattern;

public record UserChangePasswordRequestDto(

        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$",
                message = "Password must be between 8 and 20 uppercase and lowercase symbols, digit and special symbols '@#$%^&+='"
        )
        String password,

        String confirmPassword
) {
}