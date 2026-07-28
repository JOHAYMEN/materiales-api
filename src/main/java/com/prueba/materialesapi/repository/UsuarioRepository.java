package com.prueba.materialesapi.repository;

import com.prueba.materialesapi.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio encargado de las operaciones de acceso a datos
 * de la entidad {@link Usuario}.
 *
 * <p>
 * Extiende {@link JpaRepository}, proporcionando las operaciones
 * básicas de persistencia (CRUD) y define consultas específicas
 * utilizadas por el módulo de autenticación para localizar usuarios
 * registrados en la base de datos.
 * </p>
 *
 * @author Johaymen Álvarez Romero
 * @since 1.0
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca un usuario por su nombre de usuario.
     *
     * <p>
     * Este método es utilizado durante el proceso de autenticación
     * para recuperar la información del usuario registrada en la
     * base de datos.
     * </p>
     *
     * @param username nombre del usuario a consultar.
     * @return un {@link Optional} con el usuario encontrado o vacío
     * si no existe.
     */
    Optional<Usuario> findByUsername(String username);

}
