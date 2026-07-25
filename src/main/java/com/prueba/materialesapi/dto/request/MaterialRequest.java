package com.prueba.materialesapi.dto.request;

import com.prueba.materialesapi.enums.EstadoMaterial;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO utilizado para recibir la información necesaria
 * para crear o actualizar un material.
 *
 * <p>
 * Contiene los datos enviados desde el cliente y permite
 * aplicar validaciones antes de procesar la información.
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
@Schema(description = "Datos requeridos para crear un material")
public class MaterialRequest {

    @Schema(
            description = "Nombre del material",
            example = "Laptop Lenovo"
    )
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Schema(
            description = "Descripcion del material",
            example = "Computador portatil"
    )
    private String descripcion;

    @Schema(
            description = "Tipo de Material",
            example = "Tecnologico"
    )
    @NotBlank(message = "El tipo es obligatorio")
    private String tipo;

    @Schema(
            description = "Precio del material",
            example = "25000"
    )
    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor que cero")
    private BigDecimal precio;

    @Schema(
            description = "Fecha de compra del material",
            example = "2026-07-10"
    )
    @NotNull(message = "La fecha de compra es obligatoria")
    private LocalDate fechaCompra;

    private LocalDate fechaVenta;

    @Schema(
            description = "Estado del material",
            example = "ACTIVO"
    )
    @NotNull(message = "El estado es obligatorio")
    private EstadoMaterial estado;

    @Schema(
            description = "Ciudad donde se encuentra el material",
            example = "1"
    )
    @NotNull(message = "La ciudad es obligatoria")
    private Long ciudadId;

}

