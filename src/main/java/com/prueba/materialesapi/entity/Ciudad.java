package com.prueba.materialesapi.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad que representa una ciudad almacenada
 * en el sistema.
 *
 * <p>
 * Contiene la información principal de la ciudad y su relación
 * con el departamento asociada.
 * </p>
 *
 * @author Johaymen
 * @version 1.0
 */
@Entity
@Table(name = "ciudades")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ciudad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(nullable = false)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departamento_id", nullable = false)
    private Departamento departamento;

}
