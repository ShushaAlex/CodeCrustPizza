package org.telran.codecrustpizza.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSignInRequestDto {

    private String login;

    private String password;
}