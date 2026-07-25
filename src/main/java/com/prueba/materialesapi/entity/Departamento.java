package com.prueba.materialesapi.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad que representa un departamento almacenado
 * en el sistema.
 *
 * <p>
 * Contiene la información principal del departamento
 * </p>
 *
 * @author Johaymen
 * @version 1.0
 */
@Entity
@Table(name = "departamentos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(nullable = false)
    private String nombre;

}
