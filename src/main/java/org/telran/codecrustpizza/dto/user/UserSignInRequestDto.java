package org.telran.codecrustpizza.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSignInRequestDto {

    @NotBlank
    private String login;

    @NotBlank
    private String password;
}