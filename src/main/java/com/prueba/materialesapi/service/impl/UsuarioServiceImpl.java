package com.prueba.materialesapi.service.impl;

import com.prueba.materialesapi.entity.Usuario;
import com.prueba.materialesapi.repository.UsuarioRepository;
import com.prueba.materialesapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {


    private final UsuarioRepository usuarioRepository;


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