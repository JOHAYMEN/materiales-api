package com.prueba.materialesapi.repository;

import com.prueba.materialesapi.entity.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Repositorio encargado de gestionar la persistencia
 * de la entidad Departamento.
 *
 * <p>
 * Extiende las operaciones básicas proporcionadas por
 * Spring Data JPA y permite realizar consultas personalizadas.
 * </p>
 *
 * @author Johaymen
 * @version 1.0
 */
public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {

}