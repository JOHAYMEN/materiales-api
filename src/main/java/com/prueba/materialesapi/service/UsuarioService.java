package com.prueba.materialesapi.service;

import com.prueba.materialesapi.entity.Usuario;

/**
 * Define las operaciones relacionadas con la gestión de usuarios.
 *
 * <p>
 * Esta interfaz establece los métodos necesarios para consultar la
 * información de los usuarios registrados en el sistema. Es utilizada
 * principalmente por el módulo de autenticación basado en Spring Security
 * para recuperar los datos de un usuario durante el proceso de inicio de sesión.
 * </p>
 *
 * @author Johaymen Álvarez Romero
 * @since 1.0
 */
public interface UsuarioService {
    /**
     * Busca un usuario a partir de su nombre de usuario.
     *
     * @param username nombre del usuario a consultar.
     * @return usuario encontrado.
     */
    Usuario buscarPorUsername(String username);

}
