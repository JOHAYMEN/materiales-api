package com.prueba.materialesapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO genérico utilizado para estandarizar las respuestas
 * entregadas por la API REST.
 *
 * <p>
 * Contiene información del estado de la operación, mensaje
 * descriptivo y los datos retornados.
 * </p>
 *
 * @param <T> tipo de dato contenido en la respuesta
 *
 * @author Johaymen
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseDTO<T> {

    private int status;

    private String message;

    private T data;

}