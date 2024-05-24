package org.telran.codecrustpizza.dto.user;

import lombok.Data;

@Data
public class UserSignInRequestDto {

    private String login;

    private String password;
}