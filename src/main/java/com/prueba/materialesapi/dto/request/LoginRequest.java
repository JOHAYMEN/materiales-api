package com.prueba.materialesapi.dto.request;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO utilizado para recibir las credenciales de autenticación
 * enviadas por el cliente.
 *
 * <p>
 * Contiene el nombre de usuario y la contraseña requeridos para
 * iniciar sesión en la aplicación. La información recibida es
 * validada antes de ser procesada por el servicio de autenticación.
 * </p>
 *
 * @author Johaymen Álvarez Romero
 * @since 1.0
 */
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