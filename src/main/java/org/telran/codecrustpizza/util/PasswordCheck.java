package org.telran.codecrustpizza.util;

import org.telran.codecrustpizza.dto.user.UserChangePasswordRequestDto;

public class PasswordCheck {

    public static boolean validatePasswordsMatch(UserChangePasswordRequestDto dto) {
        return dto.password().equals(dto.confirmPassword());
    }
}