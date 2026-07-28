package com.prueba.materialesapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO utilizado para devolver el resultado de una autenticación
 * exitosa.
 *
 * <p>
 * Contiene el token JWT generado por el servidor, el cual deberá
 * ser enviado por el cliente en el encabezado
 * <strong>Authorization</strong> utilizando el esquema
 * <strong>Bearer</strong> para acceder a los recursos protegidos.
 * </p>
 *
 * @author Johaymen Álvarez Romero
 * @since 1.0
 */
@Data
@AllArgsConstructor
public class LoginResponse {

    private String token;

}
