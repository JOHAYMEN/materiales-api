package com.prueba.materialesapi.exception;

/**
 * Excepción lanzada cuando no se encuentra un material
 * duplicado.
 *
 * @author Johaymen
 * @version 1.0
 */
public class MaterialDuplicadoException extends RuntimeException {
    public MaterialDuplicadoException(String message) {
        super(message);
    }
}
