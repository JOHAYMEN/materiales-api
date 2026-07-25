package com.prueba.materialesapi.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.prueba.materialesapi.dto.response.ApiResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Manejador global de excepciones de la aplicación.
 *
 * <p>
 * Centraliza el tratamiento de errores generados durante el procesamiento
 * de las solicitudes HTTP, permitiendo retornar respuestas estandarizadas.
 * </p>
 *
 * <p>
 * Gestiona excepciones de validación, errores de formato en las peticiones,
 * recursos no encontrados y errores internos del servidor.
 * </p>
 *
 * @author Johaymen
 * @version 1.0
 */

@ControllerAdvice
@Slf4j
public class ControllerAdvisorExc {


    @ExceptionHandler(MaterialNotFoundException.class)
    public ResponseEntity<ApiResponseDTO<Void>> handleMaterialNotFound(
            MaterialNotFoundException exception) {

        log.warn("Material no encontrado: {}", exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        ApiResponseDTO.<Void>builder()
                                .status(HttpStatus.NOT_FOUND.value())
                                .message(exception.getMessage())
                                .data(null)
                                .build()
                );
    }


    @ExceptionHandler(CiudadNotFoundException.class)
    public ResponseEntity<ApiResponseDTO<Void>> handleCiudadNotFound(
            CiudadNotFoundException exception) {

        log.warn("Ciudad no encontrada: {}", exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        ApiResponseDTO.<Void>builder()
                                .status(HttpStatus.NOT_FOUND.value())
                                .message(exception.getMessage())
                                .data(null)
                                .build()
                );
    }


    @ExceptionHandler(FechaCompraInvalidaException.class)
    public ResponseEntity<ApiResponseDTO<Void>> handleFechaCompraInvalida(
            FechaCompraInvalidaException exception) {

        log.warn("Fecha de compra inválida: {}", exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ApiResponseDTO.<Void>builder()
                                .status(HttpStatus.BAD_REQUEST.value())
                                .message(exception.getMessage())
                                .data(null)
                                .build()
                );
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO<Void>> handleValidationException(
            MethodArgumentNotValidException exception) {


        String mensaje = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse(Constants.ERROR_DE_VALIDACION);

        log.warn("Error de validación en request: {}", mensaje);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ApiResponseDTO.<Void>builder()
                                .status(HttpStatus.BAD_REQUEST.value())
                                .message(mensaje)
                                .data(null)
                                .build()
                );
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO<Void>> handleGeneralException(
            Exception exception) {

        log.error("Error interno del servidor", exception);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ApiResponseDTO.<Void>builder()
                                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .message(Constants.ERROR_INTERNO)
                                .data(null)
                                .build()
                );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponseDTO<Void>> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex) {

        log.warn("JSON inválido recibido. Causa: {}", ex.getCause().getMessage());
        String mensaje = Constants.JSON_INVALIDO;

        Throwable cause = ex.getCause();

        if (cause instanceof InvalidFormatException invalidFormatException) {

            String campo = invalidFormatException.getPath().stream()
                    .findFirst()
                    .map(JsonMappingException.Reference::getFieldName)
                    .orElse(Constants.DESCONOCIDO);

            Object valor = invalidFormatException.getValue();

            Class<?> tipo = invalidFormatException.getTargetType();

            if (tipo.isEnum()) {

                String valoresPermitidos = Arrays.stream(tipo.getEnumConstants())
                        .map(Object::toString)
                        .collect(Collectors.joining(", "));

                mensaje = String.format(
                        Constants.ENUM_INVALIDO,
                        valor,
                        campo,
                        valoresPermitidos
                );

            } else if (Long.class.equals(tipo) || long.class.equals(tipo)) {

                mensaje = String.format(Constants.LONG_INVALIDO, campo);

            } else if (Integer.class.equals(tipo) || int.class.equals(tipo)) {

                mensaje = String.format(Constants.INTEGER_INVALIDO, campo);

            } else if (BigDecimal.class.equals(tipo)) {

                mensaje = String.format(Constants.DECIMAL_INVALIDO, campo);

            } else if (LocalDate.class.equals(tipo)) {

                mensaje = String.format(Constants.FECHA_INVALIDA, campo);

            } else if (Boolean.class.equals(tipo) || boolean.class.equals(tipo)) {

                mensaje = String.format(Constants.BOOLEAN_INVALIDO, campo);

            }

        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ApiResponseDTO.<Void>builder()
                                .status(HttpStatus.BAD_REQUEST.value())
                                .message(mensaje)
                                .data(null)
                                .build()
                );
    }

    @ExceptionHandler(MaterialDuplicadoException.class)
    public ResponseEntity<ApiResponseDTO<Void>> handleMaterialDuplicado(
            MaterialDuplicadoException exception) {

        log.warn("Intento de registrar material duplicado: {}", exception.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(
                        ApiResponseDTO.<Void>builder()
                                .status(HttpStatus.CONFLICT.value())
                                .message(exception.getMessage())
                                .data(null)
                                .build()
                );
    }

}
