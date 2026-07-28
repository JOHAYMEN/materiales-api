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


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtService jwtService;

    private final CustomUserDetailsService userDetailsService;



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
