package com.prueba.materialesapi.service;

import com.prueba.materialesapi.dto.request.MaterialRequest;
import com.prueba.materialesapi.dto.response.MaterialResponse;

import java.time.LocalDate;
import java.util.List;
/**
 * Define las operaciones de negocio relacionadas con la gestión
 * de materiales.
 *
 * <p>
 * Esta interfaz establece los métodos disponibles para crear,
 * consultar y actualizar materiales, delegando la implementación
 * a la capa de servicio correspondiente.
 * </p>
 *
 * @author Johaymen
 * @version 1.0
 */
public interface MaterialService {

    List<MaterialResponse> obtenerTodos();

    List<MaterialResponse> buscarPorTipo(String tipo);

    List<MaterialResponse> buscarPorFechaCompra(LocalDate fechaCompra);

    List<MaterialResponse> buscarPorCiudad(Long ciudadId);

    MaterialResponse guardar(MaterialRequest request);

    MaterialResponse actualizar(Long id, MaterialRequest request);

}