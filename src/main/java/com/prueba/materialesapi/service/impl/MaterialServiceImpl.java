package com.prueba.materialesapi.service.impl;

import com.prueba.materialesapi.dto.request.MaterialRequest;
import com.prueba.materialesapi.dto.response.MaterialResponse;
import com.prueba.materialesapi.entity.Ciudad;
import com.prueba.materialesapi.entity.Material;
import com.prueba.materialesapi.exception.*;
import com.prueba.materialesapi.mapper.MaterialMapper;
import com.prueba.materialesapi.repository.CiudadRepository;
import com.prueba.materialesapi.repository.MaterialRepository;
import com.prueba.materialesapi.service.MaterialService;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Implementación de la lógica de negocio para la gestión
 * de materiales.
 *
 * <p>
 * Se encarga de realizar validaciones de negocio, gestionar
 * relaciones con entidades asociadas y coordinar las operaciones
 * de persistencia mediante los repositorios correspondientes.
 * </p>
 *
 * @author Johaymen
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MaterialServiceImpl implements MaterialService {


    private final MaterialRepository materialRepository;
    private final CiudadRepository ciudadRepository;
    private final MaterialMapper materialMapper;

    @Override
    public List<MaterialResponse> obtenerTodos() {

        log.info("Consultando todos los materiales registrados");

        List<Material> materiales = materialRepository.findAll();

        if(materiales.isEmpty()){

            log.warn("No existen materiales registrados");

            throw new MaterialNotFoundException(
                    Constants.MATERIAL_NO_ENCONTRADO
            );
        }

        log.info("Se encontraron {} materiales", materiales.size());

        return materiales.stream()
                .map(materialMapper::toResponse)
                .toList();
    }

    @Override
    public List<MaterialResponse> buscarPorTipo(String tipo) {

        log.info("Buscando materiales por tipo: {}", tipo);

        List<Material> materiales = materialRepository.findByTipoIgnoreCase(tipo);

        if(materiales.isEmpty()){

            log.warn("No se encontraron materiales para el tipo: {}", tipo);

            throw new MaterialNotFoundException(
                    Constants.MATERIAL_NO_ENCONTRADO
            );
        }

        log.info("Materiales encontrados para tipo {}: {}", tipo, materiales.size());

        return materiales.stream()
                .map(materialMapper::toResponse)
                .toList();
    }

    @Override
    public List<MaterialResponse> buscarPorFechaCompra(LocalDate fechaCompra) {

        log.info("Buscando materiales por fecha de compra: {}", fechaCompra);

        List<Material> materiales = materialRepository.findByFechaCompra(fechaCompra);

        if(materiales.isEmpty()){

            log.warn("No existen materiales registrados para la fecha: {}", fechaCompra);

            throw new MaterialNotFoundException(
                    Constants.MATERIAL_NO_ENCONTRADO
            );
        }

        log.info("Materiales encontrados para fecha de compra {}: {}", fechaCompra, materiales.size());

        return materiales.stream()
                .map(materialMapper::toResponse)
                .toList();
    }


    @Override
    public List<MaterialResponse> buscarPorCiudad(Long ciudadId) {

        log.info("Buscando materiales asociados a la ciudad id: {}", ciudadId);

        List<Material> materiales = materialRepository.findByCiudadId(ciudadId);

        if(materiales.isEmpty()){

            log.warn("No existen materiales asociados a la ciudad id: {}", ciudadId);

            throw new MaterialNotFoundException(
                    Constants.MATERIAL_NO_ENCONTRADO
            );
        }

        log.info("Materiales encontrados para la ciudad {}: {}", ciudadId, materiales.size());

        return materiales.stream()
                .map(materialMapper::toResponse)
                .toList();
    }


    @Override
    public MaterialResponse guardar(MaterialRequest request) {

        log.debug("Iniciando validaciones para guardar material: {}", request.getNombre());

        validarFechas(request);

        validarMaterialDuplicado(request.getNombre(), request.getDescripcion(),null);

        log.info("Registrando nuevo material con nombre: {}", request.getNombre());

        Ciudad ciudad = ciudadRepository.findById(request.getCiudadId())
                .orElseThrow(() ->
                        new CiudadNotFoundException(
                                Constants.CIUDAD_NO_ENCONTRADA + " con id: " + request.getCiudadId()
                        ));


        Material material = materialMapper.toEntity(request);

        material.setCiudad(ciudad);

        Material guardado = materialRepository.save(material);

        log.info("Material creado correctamente con id: {}", guardado.getId());

        return materialMapper.toResponse(guardado);
    }


    @Override
    public MaterialResponse actualizar(Long id, MaterialRequest request) {

        log.debug("Iniciando validaciones para actualizar material: {}", request.getNombre());

        validarFechas(request);

        Material material = materialRepository.findById(id)
                .orElseThrow(() ->
                        new MaterialNotFoundException(Constants.MATERIAL_NO_ENCONTRADO + " con id: " + id ));

        Ciudad ciudad = ciudadRepository.findById(request.getCiudadId())
                .orElseThrow(() ->
                        new CiudadNotFoundException(Constants.CIUDAD_NO_ENCONTRADA + " con id: " + id ));


        material.setNombre(request.getNombre());
        material.setDescripcion(request.getDescripcion());
        material.setTipo(request.getTipo());
        material.setPrecio(request.getPrecio());
        material.setFechaCompra(request.getFechaCompra());
        material.setFechaVenta(request.getFechaVenta());
        material.setEstado(request.getEstado());
        material.setCiudad(ciudad);


        Material actualizado = materialRepository.save(material);

        log.info("Material actualizado correctamente con id: {}", actualizado.getId());

        return materialMapper.toResponse(actualizado);
    }

    private void validarFechas(MaterialRequest request){

        if(request.getFechaVenta() != null &&
                request.getFechaCompra().isAfter(request.getFechaVenta())){

            log.warn("Validación de fechas fallida. Fecha compra: {}, Fecha venta: {}",
                    request.getFechaCompra(),
                    request.getFechaVenta()
            );

            throw new FechaCompraInvalidaException(
                    Constants.FECHA_COMPRA_INVALIDA
            );
        }
    }

    private void validarMaterialDuplicado(String nombre, String descripcion, Long id) {

        boolean existe;

        if (id == null) {
            existe = materialRepository
                    .existsByNombreIgnoreCaseAndDescripcionIgnoreCase(nombre, descripcion);
        } else {
            existe = materialRepository
                    .existsByNombreIgnoreCaseAndDescripcionIgnoreCaseAndIdNot(nombre, descripcion, id);
        }

        if (existe) {
            log.warn("Validación de duplicidad fallida. Ya existe material con nombre: {} y descripción: {}",
                    nombre,
                    descripcion
            );
            throw new MaterialDuplicadoException(Constants.MATERIAL_DUPLICADO);
        }
    }

}