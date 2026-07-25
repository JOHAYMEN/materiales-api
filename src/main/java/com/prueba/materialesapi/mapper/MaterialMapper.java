package com.prueba.materialesapi.mapper;

import com.prueba.materialesapi.dto.request.MaterialRequest;
import com.prueba.materialesapi.dto.response.MaterialResponse;
import com.prueba.materialesapi.entity.Material;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Interfaz encargada de realizar la conversión entre entidades
 * y objetos de transferencia de datos (DTO).
 *
 * <p>
 * Permite transformar objetos de dominio, como la entidad Material,
 * en objetos de respuesta para la API REST y viceversa, evitando
 * acoplar la capa de presentación con la capa de persistencia.
 * </p>
 *
 * @author Johaymen
 * @version 1.0
 */
@Mapper(componentModel = "spring")
public interface MaterialMapper {


    @Mapping(
            source = "ciudad.nombre",
            target = "ciudad"
    )
    @Mapping(
            source = "ciudad.departamento.nombre",
            target = "departamento"
    )
    MaterialResponse toResponse(Material material);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ciudad", ignore = true)
    Material toEntity(MaterialRequest request);

}

