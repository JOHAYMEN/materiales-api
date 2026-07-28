package com.prueba.materialesapi.service.impl;

import com.prueba.materialesapi.entity.Usuario;
import com.prueba.materialesapi.repository.UsuarioRepository;
import com.prueba.materialesapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementación del servicio encargado de la gestión de usuarios.
 *
 * <p>
 * Proporciona las operaciones relacionadas con la consulta de usuarios
 * registrados en la base de datos. Actualmente es utilizado por
 * Spring Security durante el proceso de autenticación para recuperar
 * la información del usuario a partir de su nombre de usuario.
 * </p>
 *
 * @author Johaymen Álvarez Romero
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    /**
     * Repositorio utilizado para acceder a la información
     * de los usuarios almacenados en la base de datos.
     */
    private final UsuarioRepository usuarioRepository;

    /**
     * Busca un usuario por su nombre de usuario.
     *
     * <p>
     * Si el usuario no existe en la base de datos, se lanza una
     * excepción {@link UsernameNotFoundException}, la cual es
     * utilizada por Spring Security durante el proceso de autenticación.
     * </p>
     *
     * @param username nombre del usuario a consultar.
     * @return usuario encontrado.
     * @throws UsernameNotFoundException si el usuario no existe.
     */
    @Override
    public Usuario buscarPorUsername(String username) {

        return usuarioRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Usuario no encontrado"
                        )
                );
    }
}