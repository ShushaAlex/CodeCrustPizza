package org.telran.codecrustpizza.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.telran.codecrustpizza.dto.user.UserChangePasswordRequestDto;
import org.telran.codecrustpizza.dto.user.UserCreateRequestDto;

@Component
public class UserPasswordValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return UserCreateRequestDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserCreateRequestDto dto = (UserCreateRequestDto) target;
        UserChangePasswordRequestDto passwordDto = dto.passwordRequestDto();
        boolean isSame = passwordDto.password().equals(passwordDto.confirmPassword());
        if (!isSame) {
            errors.rejectValue("confirmPassword", "Passwords are not the same");
        }
    }
}