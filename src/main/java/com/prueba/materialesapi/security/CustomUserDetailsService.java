package com.prueba.materialesapi.security;

import com.prueba.materialesapi.entity.Usuario;
import com.prueba.materialesapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

/**
 * Implementación personalizada de {@link UserDetailsService}.
 *
 * <p>
 * Esta clase es utilizada por Spring Security para obtener la información
 * del usuario desde la base de datos durante el proceso de autenticación.
 * A partir del nombre de usuario recibido, consulta el servicio de usuarios
 * y construye un objeto {@link UserDetails} con las credenciales y roles
 * necesarios para el proceso de autenticación y autorización.
 * </p>
 *
 * @author Johaymen Álvarez Romero
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    /**
     * Servicio encargado de consultar los usuarios registrados
     * en la base de datos.
     */
    private final UsuarioService usuarioService;

    /**
     * Carga la información de un usuario a partir de su nombre de usuario.
     *
     * <p>
     * Spring Security invoca este método automáticamente durante el proceso
     * de autenticación para obtener las credenciales y los roles asociados
     * al usuario.
     * </p>
     *
     * @param username nombre del usuario a consultar.
     * @return información del usuario en formato {@link UserDetails}.
     * @throws UsernameNotFoundException si el usuario no existe.
     */
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {


        Usuario usuario = usuarioService.buscarPorUsername(username);

        if (usuario == null) {
            throw new UsernameNotFoundException(
                    "Usuario no encontrado: " + username
            );
        }

        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .roles(usuario.getRol())
                .build();
    }
}