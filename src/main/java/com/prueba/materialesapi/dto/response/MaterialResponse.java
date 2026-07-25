package com.prueba.materialesapi.dto.response;

import com.prueba.materialesapi.enums.EstadoMaterial;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO utilizado para representar la información de un material
 * que será retornada al cliente.
 *
 * <p>
 * Permite desacoplar la estructura interna de la entidad
 * de la información expuesta mediante la API REST.
 * </p>
 *
 * @author Johaymen
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaterialResponse {

    private Long id;

    private String nombre;

    private String descripcion;

    private String tipo;

    private BigDecimal precio;

    private LocalDate fechaCompra;

    private LocalDate fechaVenta;

    private EstadoMaterial estado;

    private String ciudad;

    private String departamento;

}