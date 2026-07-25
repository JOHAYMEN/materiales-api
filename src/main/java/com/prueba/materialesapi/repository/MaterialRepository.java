package com.prueba.materialesapi.repository;

import com.prueba.materialesapi.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repositorio encargado de gestionar la persistencia
 * de la entidad Material.
 *
 * <p>
 * Extiende las operaciones básicas proporcionadas por
 * Spring Data JPA y permite realizar consultas personalizadas.
 * </p>
 *
 * @author Johaymen
 * @version 1.0
 */
public interface MaterialRepository extends JpaRepository<Material, Long> {

    List<Material> findByTipoIgnoreCase(String tipo);

    List<Material> findByFechaCompra(LocalDate fechaCompra);

    List<Material> findByCiudadId(Long ciudadId);

    boolean existsByNombreIgnoreCaseAndDescripcionIgnoreCase(
            String nombre,
            String descripcion
    );

    boolean existsByNombreIgnoreCaseAndDescripcionIgnoreCaseAndIdNot(
            String nombre,
            String descripcion,
            Long id
    );

}