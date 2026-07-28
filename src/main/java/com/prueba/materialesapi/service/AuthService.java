package com.prueba.materialesapi.service;

import com.prueba.materialesapi.dto.request.LoginRequest;
import com.prueba.materialesapi.dto.response.LoginResponse;
import com.prueba.materialesapi.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado del proceso de autenticación de usuarios.
 *
 * <p>
 * Valida las credenciales recibidas mediante Spring Security y,
 * en caso de ser correctas, genera un token JWT que permitirá
 * acceder a los endpoints protegidos de la aplicación.
 * </p>
 *
 * @author Johaymen Álvarez Romero
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    /**
     * Componente de Spring Security encargado de autenticar
     * las credenciales del usuario.
     */
    private final AuthenticationManager authenticationManager;

    /**
     * Servicio encargado de generar y procesar tokens JWT.
     */
    private final JwtService jwtService;

    /**
     * Autentica un usuario y genera un token JWT.
     *
     * <p>
     * Primero valida el nombre de usuario y la contraseña utilizando
     * el {@link AuthenticationManager}. Si las credenciales son válidas,
     * genera un token JWT que será utilizado para acceder a los recursos
     * protegidos de la aplicación.
     * </p>
     *
     * @param request credenciales del usuario.
     * @return respuesta que contiene el token JWT generado.
     */
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
