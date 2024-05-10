package org.telran.codecrustpizza.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.telran.codecrustpizza.dto.user.UserChangePasswordRequestDto;

@Component
public class UserPasswordValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return UserChangePasswordRequestDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserChangePasswordRequestDto dto = (UserChangePasswordRequestDto) target;
        boolean isSame = dto.password().equals(dto.confirmPassword());
        if (!isSame) {
            errors.rejectValue("confirmPassword", "Passwords are not the same");
        }
    }
}
