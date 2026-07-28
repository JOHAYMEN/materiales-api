package com.prueba.materialesapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración de la política CORS (Cross-Origin Resource Sharing)
 * de la aplicación.
 *
 * <p>
 * Permite que aplicaciones cliente autorizadas puedan consumir
 * los endpoints REST expuestos por la API desde un origen diferente.
 * En este proyecto se habilita el acceso desde la aplicación
 * Angular ejecutándose en <strong>http://localhost:4200</strong>.
 * </p>
 *
 * @author Johaymen Álvarez Romero
 * @since 1.0
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     * Configura los orígenes, métodos HTTP y encabezados permitidos
     * para realizar solicitudes a la API.
     *
     * @param registry registro utilizado para definir las reglas CORS
     *                 de la aplicación.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "OPTIONS"
                )
                .allowedHeaders("*");

    }

}