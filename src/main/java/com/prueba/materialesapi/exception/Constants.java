package com.prueba.materialesapi.exception;
/**
 * Clase que contiene las constantes utilizadas en la aplicación.
 *
 * <p>
 * Centraliza mensajes de respuesta, validaciones y textos reutilizados
 * dentro de la lógica de negocio y manejo de excepciones, evitando
 * la duplicidad de cadenas de texto en el código fuente.
 * </p>
 *
 * @author Johaymen
 * @version 1.0
 */
public class Constants {

    private Constants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String MATERIAL_NO_ENCONTRADO =
            "Material no encontrado";

    public static final String MATERIAL_CREADO =
            "Material creado correctamente";

    public static final String MATERIAL_ENCONTRADO =
            "Materiales encontrados correctamente";

    public static final String MATERIAL_ACTUALIZADO =
            "Material actualizado correctamente";

    public static final String CIUDAD_NO_ENCONTRADA =
            "Ciudad no encontrada";

    public static final String FECHA_COMPRA_INVALIDA =
            "La fecha de compra no puede ser superior a la fecha de venta";

    public static final String ERROR_INTERNO =
            "Error interno del servidor";

    public static final String MATERIAL_DUPLICADO =
            "Ya existe un material con el mismo nombre y descripción.";

    public static final String ERROR_DE_VALIDACION =
            "Error de validación";

    public static final String DESCONOCIDO =
            "desconocido";

    public static final String JSON_INVALIDO =
            "El cuerpo de la solicitud contiene un JSON inválido.";

    public static final String ENUM_INVALIDO =
            "El valor '%s' no es válido para el campo '%s'. Valores permitidos: %s.";

    public static final String LONG_INVALIDO =
            "El campo '%s' debe ser un número entero.";

    public static final String INTEGER_INVALIDO =
            "El campo '%s' debe ser un número entero.";

    public static final String DECIMAL_INVALIDO =
            "El campo '%s' debe ser un número decimal.";

    public static final String FECHA_INVALIDA =
            "El campo '%s' debe tener el formato yyyy-MM-dd.";

    public static final String BOOLEAN_INVALIDO =
            "El campo '%s' debe ser verdadero o falso.";

    public static final String SWAGGER_TITLE_MESSAGE = "API Materiales by Johaymen Alvarez";
    public static final String SWAGGER_DESCRIPTION_MESSAGE = "Materiales";
    public static final String SWAGGER_VERSION_MESSAGE = "1.0.0";
    public static final String SWAGGER_LICENSE_NAME_MESSAGE = "Apache 2.0";
    public static final String SWAGGER_LICENSE_URL_MESSAGE = "http://springdoc.org";
    public static final String SWAGGER_TERMS_OF_SERVICE_MESSAGE = "http://swagger.io/terms/";
}
