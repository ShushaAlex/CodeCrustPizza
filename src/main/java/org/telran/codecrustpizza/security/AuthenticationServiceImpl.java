package org.telran.codecrustpizza.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.telran.codecrustpizza.dto.JwtAuthenticationResponse;
import org.telran.codecrustpizza.dto.user.UserSignInRequestDto;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Override
    public JwtAuthenticationResponse authenticate(UserSignInRequestDto request) {
        //По факту здесь будет проверка нашего логина и пароля с нашим пользователем в базе данных
        //как и при базовой аутентификации, просто обернуто через менеджера
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getLogin(),
                        request.getPassword()));

        //Если с аутентификацией проблемы, то выше будет выброшено исключение
        //если проблем нет, то генерируем токен для этого пользователя

        UserDetails user = userDetailsService.loadUserByUsername(request.getLogin());

        String token = jwtService.generateToken(user);
        // для интереса, сгенерированный токен можно проверить на сайте jwt.io
        return new JwtAuthenticationResponse(token);
    }
}