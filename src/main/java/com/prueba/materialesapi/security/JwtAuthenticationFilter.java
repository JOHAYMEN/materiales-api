package com.prueba.materialesapi.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;

/**
 * Filtro de autenticación basado en JSON Web Token (JWT).
 *
 * <p>
 * Intercepta todas las solicitudes HTTP dirigidas a la aplicación para
 * verificar si contienen un token JWT válido en el encabezado
 * <strong>Authorization</strong>.
 * </p>
 *
 * <p>
 * Cuando el token es válido, el filtro extrae el nombre del usuario,
 * carga sus datos desde la base de datos y registra la autenticación
 * en el contexto de seguridad de Spring Security, permitiendo el acceso
 * a los recursos protegidos.
 * </p>
 *
 * @author Johaymen Álvarez Romero
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * Servicio encargado de generar y validar tokens JWT.
     */
    private final JwtService jwtService;
    /**
     * Servicio encargado de obtener la información del usuario autenticado.
     */
    private final CustomUserDetailsService userDetailsService;


    /**
     * Procesa cada solicitud HTTP para validar la autenticación mediante JWT.
     *
     * <p>
     * El flujo realizado por este filtro es el siguiente:
     * </p>
     *
     * <ol>
     *     <li>Obtiene el encabezado Authorization.</li>
     *     <li>Verifica que el encabezado contenga un token Bearer.</li>
     *     <li>Extrae el nombre del usuario desde el token JWT.</li>
     *     <li>Consulta la información del usuario.</li>
     *     <li>Crea el objeto de autenticación.</li>
     *     <li>Registra la autenticación en el contexto de Spring Security.</li>
     *     <li>Continúa con el procesamiento normal de la solicitud.</li>
     * </ol>
     *
     * @param request solicitud HTTP recibida.
     * @param response respuesta HTTP.
     * @param filterChain cadena de filtros configurados por Spring Security.
     * @throws ServletException si ocurre un error durante el procesamiento.
     * @throws IOException si ocurre un error de entrada o salida.
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {


        final String authHeader =
                request.getHeader("Authorization");


        final String jwt;

        final String username;


        /*
         * Si no viene Authorization
         * o no empieza con Bearer,
         * continúa la petición normalmente
         */
        if(authHeader == null ||
                !authHeader.startsWith("Bearer ")){

            filterChain.doFilter(request,response);
            return;
        }


        /*
         * Quitamos "Bearer "
         */
        jwt = authHeader.substring(7);


        /*
         * Extraemos usuario del token
         */
        username = jwtService.extractUsername(jwt);



        /*
         * Si hay usuario y todavía no está autenticado
         */
        if(username != null &&
                SecurityContextHolder.getContext()
                        .getAuthentication() == null){


            UserDetails userDetails =
                    userDetailsService
                            .loadUserByUsername(username);



            /*
             * Creamos autenticación
             */
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );


            authToken.setDetails(
                    new WebAuthenticationDetailsSource()
                            .buildDetails(request)
            );


            /*
             * Guardamos autenticación en Spring Security
             */
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authToken);

        }


        filterChain.doFilter(request,response);

    }
}
