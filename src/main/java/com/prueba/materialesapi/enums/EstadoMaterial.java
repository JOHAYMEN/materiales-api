package com.prueba.materialesapi.enums;
/**
 * Enumeración que representa los posibles estados de un material dentro del sistema.
 *
 * <p>Los estados disponibles son:</p>
 * <ul>
 *   <li><b>ACTIVO:</b> El material se encuentra registrado y habilitado en el sistema.</li>
 *   <li><b>DISPONIBLE:</b> El material está disponible para ser asignado.</li>
 *   <li><b>ASIGNADO:</b> El material ya fue asignado y no se encuentra disponible.</li>
 * </ul>
 *
 * @author Johaymen Alvarez Romero
 * @since 1.0
 */
public enum EstadoMaterial {
    ACTIVO,
    DISPONIBLE,
    ASIGNADO
}
