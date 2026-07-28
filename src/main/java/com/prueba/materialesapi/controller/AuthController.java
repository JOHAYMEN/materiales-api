package com.prueba.materialesapi.controller;

import com.prueba.materialesapi.dto.request.LoginRequest;
import com.prueba.materialesapi.dto.response.LoginResponse;
import com.prueba.materialesapi.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador encargado de gestionar las operaciones de autenticación
 * de la aplicación.
 *
 * <p>
 * Expone los endpoints públicos relacionados con el inicio de sesión.
 * Una vez autenticado el usuario, devuelve un token JWT que permitirá
 * consumir los recursos protegidos por Spring Security.
 * </p>
 *
 * @author Johaymen Álvarez Romero
 * @since 1.0
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    /**
     * Servicio encargado de validar las credenciales del usuario
     * y generar el token JWT.
     */
    private final AuthService authService;

    /**
     * Autentica un usuario y devuelve un token JWT.
     *
     * <p>
     * Recibe las credenciales del usuario, valida la autenticación
     * mediante Spring Security y, si el proceso es exitoso,
     * retorna un token JWT que deberá enviarse en el encabezado
     * <strong>Authorization</strong> de las solicitudes posteriores.
     * </p>
     *
     * @param request credenciales del usuario.
     * @return respuesta con el token JWT generado.
     */
    @Operation(
            summary = "Autenticar usuario",
            description = "Permite autenticar un usuario y obtener un token JWT para acceder a los endpoints protegidos"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario autenticado correctamente" ),
            @ApiResponse(responseCode = "400", description = "Datos de autenticación inválidos"),
            @ApiResponse(responseCode = "401", description = "Usuario o contraseña incorrectos"),
            @ApiResponse(responseCode = "500", description = "Error interno en servidor")})
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){

        return ResponseEntity.ok(
                authService.login(request)
        );

    }

}
