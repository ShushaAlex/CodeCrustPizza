package org.telran.codecrustpizza.security;


import org.telran.codecrustpizza.dto.JwtAuthenticationResponse;
import org.telran.codecrustpizza.dto.user.UserSignInRequestDto;

public interface AuthenticationService {

    JwtAuthenticationResponse authenticate(UserSignInRequestDto request);
}
