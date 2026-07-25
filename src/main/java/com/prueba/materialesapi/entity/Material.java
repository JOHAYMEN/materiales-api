package com.prueba.materialesapi.entity;

import com.prueba.materialesapi.enums.EstadoMaterial;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entidad que representa un material almacenado
 * en el sistema.
 *
 * <p>
 * Contiene la información principal del material y su relación
 * con la ciudad asociada.
 * </p>
 *
 * @author Johaymen
 * @version 1.0
 */
@Entity
@Table(name = "materiales")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(length = 500)
    private String descripcion;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(nullable = false)
    private LocalDate fechaCompra;

    private LocalDate fechaVenta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoMaterial estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ciudad_id", nullable = false)
    private Ciudad ciudad;

}
