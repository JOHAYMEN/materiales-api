package com.prueba.materialesapi.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Representa un usuario del sistema con permisos para
 * autenticarse en la aplicación mediante Spring Security y JWT.
 *
 * <p>
 * La información almacenada en esta entidad es utilizada durante
 * el proceso de autenticación para validar las credenciales del
 * usuario y determinar el rol asignado dentro de la aplicación.
 * </p>
 *
 * @author Johaymen Álvarez Romero
 * @since 1.0
 */
@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    /**
     * Identificador único del usuario.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre de usuario utilizado para iniciar sesión.
     */
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * Contraseña del usuario almacenada de forma encriptada
     * mediante BCrypt.
     */
    @Column(nullable = false)
    private String password;

    /**
     * Rol asignado al usuario dentro de la aplicación.
     */
    private String rol;

}