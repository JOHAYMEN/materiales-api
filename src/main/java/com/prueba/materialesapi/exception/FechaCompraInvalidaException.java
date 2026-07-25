package com.prueba.materialesapi.exception;

/**
 * Excepción lanzada cuando no se encuentra una fecha de compra
 * asociado al criterio de búsqueda es invalida.
 *
 * @author Johaymen
 * @version 1.0
 */
public class FechaCompraInvalidaException extends RuntimeException{
    public FechaCompraInvalidaException(String message) {
        super(message);
    }
}
