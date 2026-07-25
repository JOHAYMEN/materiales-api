package com.prueba.materialesapi.exception;

/**
 * Excepción lanzada cuando no se encuentra una ciudad
 * asociado al criterio de búsqueda solicitado.
 *
 * @author Johaymen
 * @version 1.0
 */
public class CiudadNotFoundException extends RuntimeException {
    public CiudadNotFoundException(String message) {
        super(message);
    }
}