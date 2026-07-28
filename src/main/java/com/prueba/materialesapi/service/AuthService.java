package com.prueba.materialesapi.service;

import com.prueba.materialesapi.dto.request.LoginRequest;
import com.prueba.materialesapi.dto.response.LoginResponse;
import com.prueba.materialesapi.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {


    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;


    public LoginResponse login(LoginRequest request) {


        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );


        String token = jwtService.generateToken(
                request.getUsername()
        );


        return new LoginResponse(token);
    }
}
