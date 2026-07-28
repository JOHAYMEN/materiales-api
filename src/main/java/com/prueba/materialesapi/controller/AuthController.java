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


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;


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
