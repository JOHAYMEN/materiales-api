package com.prueba.materialesapi.repository;

import com.prueba.materialesapi.entity.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/**
 * Repositorio encargado de gestionar la persistencia
 * de la entidad Ciudad.
 *
 * <p>
 * Extiende las operaciones básicas proporcionadas por
 * Spring Data JPA y permite realizar consultas personalizadas.
 * </p>
 *
 * @author Johaymen
 * @version 1.0
 */
public interface CiudadRepository extends JpaRepository<Ciudad, Long> {

    List<Ciudad> findByNombreContainingIgnoreCase(String nombre);

}