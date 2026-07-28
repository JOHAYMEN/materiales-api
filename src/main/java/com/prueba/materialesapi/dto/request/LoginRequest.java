package com.prueba.materialesapi.dto.request;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {

    @Schema(
            description = "username para logueo",
            example = "admin"
    )
    @NotNull(message = "El username es obligatorio")
    private String username;

    @Schema(
            description = "Contraseña para logueo",
            example = "admin123"
    )
    @NotNull(message = "El password es obligatorio")
    private String password;

}