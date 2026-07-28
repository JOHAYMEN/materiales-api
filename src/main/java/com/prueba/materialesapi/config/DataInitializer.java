package com.prueba.materialesapi.config;

import com.prueba.materialesapi.entity.Usuario;
import com.prueba.materialesapi.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {


    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;



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


            System.out.println(
                    "Usuario admin creado correctamente"
            );
        }

    }
}
