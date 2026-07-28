package com.prueba.materialesapi.config;

import com.prueba.materialesapi.entity.Usuario;
import com.prueba.materialesapi.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Inicializa los datos necesarios para el funcionamiento de la aplicación.
 *
 * <p>
 * Al iniciar la aplicación verifica la existencia de un usuario administrador.
 * Si este no se encuentra registrado en la base de datos, crea automáticamente
 * un usuario con credenciales por defecto para facilitar las pruebas y el acceso
 * inicial al sistema.
 * </p>
 *
 * @author Johaymen Álvarez Romero
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private static final Logger LOGGER = LogManager.getLogger(DataInitializer.class);

    /**
     * Repositorio utilizado para consultar y almacenar usuarios.
     */
    private final UsuarioRepository usuarioRepository;
    /**
     * Componente encargado de encriptar las contraseñas mediante BCrypt.
     */
    private final PasswordEncoder passwordEncoder;


    /**
     * Ejecuta la inicialización de datos al iniciar la aplicación.
     *
     * <p>
     * Si no existe un usuario con nombre <strong>admin</strong>,
     * crea uno automáticamente con la contraseña encriptada y el
     * rol de administrador.
     * </p>
     *
     * @param args argumentos recibidos al iniciar la aplicación.
     * @throws Exception si ocurre algún error durante la inicialización.
     */
    @Override
    public void run(String... args) throws Exception {


        if(usuarioRepository.findByUsername("admin").isEmpty()){


            Usuario usuario = new Usuario();

            usuario.setUsername("admin");

            usuario.setPassword(
                    passwordEncoder.encode("admin123")
            );

            usuario.setRol("ADMIN");


            usuarioRepository.save(usuario);


            LOGGER.info("Usuario administrador creado correctamente.");
        }

    }
}
