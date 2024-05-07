package org.telran.codecrustpizza.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.telran.codecrustpizza.dto.user.UserCreateRequestDto;
import org.telran.codecrustpizza.repository.UserRepository;

@Component
public class UserCreateValidator implements Validator {

    private final UserRepository userRepository;

    @Autowired
    public UserCreateValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserCreateRequestDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserCreateRequestDto dto = (UserCreateRequestDto) target;
        boolean isSame = dto.password().equals(dto.confirmPassword());
        if (!isSame) {
            errors.rejectValue("confirmPassword", "Passwords are not the same");
        }
        userRepository.findByEmail(dto.email()).ifPresent(e -> errors.rejectValue("email", "This email is already registered"));
    }
}
