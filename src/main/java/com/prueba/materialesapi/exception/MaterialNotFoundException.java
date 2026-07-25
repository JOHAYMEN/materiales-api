package com.prueba.materialesapi.exception;

/**
 * Excepción lanzada cuando no se encuentra un material
 * asociado al criterio de búsqueda solicitado.
 *
 * @author Johaymen
 * @version 1.0
 */
public class MaterialNotFoundException extends RuntimeException {
    public MaterialNotFoundException(String message) {
        super(message);
    }
}
