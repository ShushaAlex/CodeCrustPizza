package org.telran.codecrustpizza.security;


import org.telran.codecrustpizza.security.model.JwtAuthenticationResponse;
import org.telran.codecrustpizza.security.model.SignInRequest;

public interface AuthenticationService {

    JwtAuthenticationResponse authenticate(SignInRequest request);
}
