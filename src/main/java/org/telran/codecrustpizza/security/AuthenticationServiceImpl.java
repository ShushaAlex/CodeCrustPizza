package org.telran.codecrustpizza.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.telran.codecrustpizza.security.model.JwtAuthenticationResponse;
import org.telran.codecrustpizza.security.model.SignInRequest;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;

    @Override
    public JwtAuthenticationResponse authenticate(SignInRequest request) {
        return null;
    }
}
