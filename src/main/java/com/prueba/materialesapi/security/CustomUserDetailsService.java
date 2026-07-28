package com.prueba.materialesapi.security;

import com.prueba.materialesapi.entity.Usuario;
import com.prueba.materialesapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService
        implements UserDetailsService {


    private final UsuarioService usuarioService;


    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {


        Usuario usuario =
                usuarioService.buscarPorUsername(username);


        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .roles(usuario.getRol())
                .build();
    }
}