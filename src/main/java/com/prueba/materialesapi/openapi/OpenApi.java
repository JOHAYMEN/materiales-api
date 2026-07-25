package com.prueba.materialesapi.openapi;
import com.prueba.materialesapi.exception.Constants;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de la documentación OpenAPI de la aplicación.
 *
 * <p>
 * Define la información general utilizada por Swagger para documentar
 * los endpoints expuestos por la API REST, incluyendo título,
 * descripción y versión del servicio.
 * </p>
 *
 * @author Johaymen
 * @version 1.0
 */
@Configuration
public class OpenApi {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(Constants.SWAGGER_TITLE_MESSAGE)
                        .description(Constants.SWAGGER_DESCRIPTION_MESSAGE)
                        .version(Constants.SWAGGER_VERSION_MESSAGE)
                        .license(new License().name(Constants.SWAGGER_LICENSE_NAME_MESSAGE).url(Constants.SWAGGER_LICENSE_URL_MESSAGE))
                        .termsOfService(Constants.SWAGGER_TERMS_OF_SERVICE_MESSAGE))
                        .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
    }
}
