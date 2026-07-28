package com.prueba.materialesapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
/**
 * Configuración de seguridad de la aplicación.
 *
 * <p>
 * Define la configuración de Spring Security para la autenticación
 * mediante JWT, el manejo de CORS, la política de sesiones,
 * el proveedor de autenticación y el codificador de contraseñas.
 * También especifica los endpoints públicos y protegidos
 * de la API.
 * </p>
 *
 * @author Johaymen Alvarez Romero
 * @since 1.0
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {


    private final CustomUserDetailsService userDetailsService;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Configura la cadena de filtros de Spring Security.
     *
     * <p>
     * Deshabilita CSRF, habilita CORS, configura la aplicación
     * para trabajar sin sesiones (STATELESS), permite el acceso
     * público a los endpoints de autenticación y documentación
     * Swagger e incorpora el filtro JWT antes del filtro
     * de autenticación por usuario y contraseña.
     * </p>
     *
     * @param http configuración de seguridad HTTP.
     * @return cadena de filtros de seguridad.
     * @throws Exception si ocurre un error durante la configuración.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {


        http
                .csrf(csrf -> csrf.disable())

                .cors(cors -> {})

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/api/auth/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        )
                        .permitAll()

                        .anyRequest()
                        .authenticated()
                );

                http.addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );


        return http.build();
    }


    /**
     * Configura el proveedor de autenticación basado
     * en la información de usuarios almacenada en la base de datos.
     *
     * @return proveedor de autenticación.
     */
    @Bean
    public AuthenticationProvider authenticationProvider(){

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();


        provider.setUserDetailsService(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder());


        return provider;
    }
    /**
     * Configura las políticas CORS de la aplicación.
     *
     * <p>
     * Permite solicitudes desde el cliente Angular
     * ejecutándose en localhost:4200, habilitando los
     * métodos HTTP utilizados por la API.
     * </p>
     *
     * @return configuración CORS.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(
                List.of("http://localhost:4200")
        );

        configuration.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "OPTIONS"
                )
        );

        configuration.setAllowedHeaders(
                List.of("*")
        );

        configuration.setAllowCredentials(true);


        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();


        source.registerCorsConfiguration(
                "/**",
                configuration
        );


        return source;
    }

    /**
     * Crea el codificador de contraseñas utilizado
     * por Spring Security.
     *
     * @return instancia de BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();

    }

    /**
     * Obtiene el administrador de autenticación de Spring Security.
     *
     * @param configuration configuración de autenticación.
     * @return administrador de autenticación.
     * @throws Exception si ocurre un error al obtener el administrador.
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {

        return configuration.getAuthenticationManager();

    }

}
