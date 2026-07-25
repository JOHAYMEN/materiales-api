package com.prueba.materialesapi.controller;


import com.prueba.materialesapi.dto.request.MaterialRequest;
import com.prueba.materialesapi.dto.response.ApiResponseDTO;
import com.prueba.materialesapi.dto.response.MaterialResponse;
import com.prueba.materialesapi.exception.Constants;
import com.prueba.materialesapi.service.MaterialService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.List;

/**
 * Controlador REST encargado de gestionar las operaciones relacionadas
 * con materiales.
 *
 * <p>Expone endpoints que permite crear, consultar, actualizar materiales.</p>
 *
 * @author Johaymen
 * @version 1.0
 */
@RestController
@RequestMapping("/api/materiales")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService materialService;

    /**
     * Registra un nuevo material.
     *
     * <p>
     * Antes de guardar la información se realizan validaciones
     * de negocio como existencia de la ciudad asociada y
     * duplicidad del material.
     * </p>
     *
     * @param request información requerida para crear el material
     * @return respuesta con la información del material creado
     *
     * @throws CiudadNotFoundException cuando la ciudad enviada
     *         no existe
     *
     * @throws MaterialAlreadyExistsException cuando ya existe
     *         un material con el mismo nombre y descripción
     */
    @Operation(summary = "Crear material", description = "Permite crear un nuevo material")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Material creado correctamente" ),
            @ApiResponse(responseCode = "400", description = "Datos inválidos enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno en servidor")})

    @PostMapping
    public ResponseEntity<ApiResponseDTO<MaterialResponse>> guardar(
            @Valid @RequestBody MaterialRequest request) {

        MaterialResponse material = materialService.guardar(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponseDTO.<MaterialResponse>builder()
                                .status(HttpStatus.CREATED.value())
                                .message(Constants.MATERIAL_CREADO)
                                .data(material)
                                .build()
                );
    }


    /**
     * Obtiene todos los materiales registrados.
     *
     * <p>
     * Retorna la información resumida de cada material almacenado
     * en la base de datos.
     * </p>
     *
     * @return lista de materiales disponibles
     */
    @Operation(summary = "Consultar todos los material", description = "Obtiene la lista completa de materiales registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Materiales encontrados correctamente" ),
            @ApiResponse(responseCode = "404", description = "No se encontraron resultados"),
            @ApiResponse(responseCode = "500", description = "Error interno en servidor")})

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<MaterialResponse>>> obtenerTodos() {


        List<MaterialResponse> materiales = materialService.obtenerTodos();


        return ResponseEntity.ok(
                ApiResponseDTO.<List<MaterialResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message(Constants.MATERIAL_ENCONTRADO)
                        .data(materiales)
                        .build()
        );
    }

    /**
     * Consulta los materiales registrados filtrando por tipo.
     *
     * <p>
     * Permite obtener una lista de materiales que coincidan
     * con el tipo recibido como parámetro.
     * </p>
     *
     * @param tipo tipo de material que se desea consultar
     * @return lista de materiales que coinciden con el tipo indicado
     *
     * @throws MaterialNotFoundException cuando no existen materiales
     *         registrados con el tipo solicitado
     */
    @Operation(summary = "Consulta materiales por tipo", description = "Permite buscar todos los materiales por tipo, ejemplo: Tecnologico, Hogar, etc")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de material encontrados correctamente" ),
            @ApiResponse(responseCode = "404", description = "No se encontraron resultados"),
            @ApiResponse(responseCode = "500", description = "Error interno en servidor")})

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<ApiResponseDTO<List<MaterialResponse>>> buscarPorTipo(
            @PathVariable String tipo) {

        List<MaterialResponse> materiales = materialService.buscarPorTipo(tipo);

        return ResponseEntity.ok(
                ApiResponseDTO.<List<MaterialResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message(Constants.MATERIAL_ENCONTRADO)
                        .data(materiales)
                        .build()
        );
    }

    /**
     * Consulta los materiales registrados por fecha de compra.
     *
     * <p>
     * La fecha debe ser enviada utilizando el formato:
     * yyyy-MM-dd.
     * </p>
     *
     * @param fecha fecha de compra utilizada para realizar la búsqueda
     * @return lista de materiales registrados en la fecha indicada
     *
     * @throws MaterialNotFoundException cuando no existen materiales
     *         asociados a la fecha enviada
     */
    @Operation(summary = "Consulta material por fecha de compra", description = "Permite buscar materiales por fecha de compra, formato: yyyy-MM-dd")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Materiales encontrados correctamente" ),
            @ApiResponse(responseCode = "404", description = "No se encontraron resultados"),
            @ApiResponse(responseCode = "500", description = "Error interno en servidor")})

    @GetMapping("/fecha-compra/{fecha}")
    public ResponseEntity<ApiResponseDTO<List<MaterialResponse>>> buscarPorFechaCompra(
            @PathVariable LocalDate fecha) {

        List<MaterialResponse> materiales = materialService.buscarPorFechaCompra(fecha);

        return ResponseEntity.ok(
                ApiResponseDTO.<List<MaterialResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message(Constants.MATERIAL_ENCONTRADO)
                        .data(materiales)
                        .build()
        );
    }

    /**
     * Consulta los materiales asociados a una ciudad específica.
     *
     * <p>
     * Permite obtener los materiales registrados utilizando
     * el identificador de la ciudad como criterio de búsqueda.
     * </p>
     *
     * @param ciudadId identificador de la ciudad asociada al material
     * @return lista de materiales pertenecientes a la ciudad indicada
     *
     * @throws CiudadNotFoundException cuando la ciudad no existe
     * @throws MaterialNotFoundException cuando no existen materiales
     *         asociados a la ciudad indicada
     */
    @Operation(summary = "Consulta material por ciudad", description = "Permite buscar materiales por el id de la ciudad. por ejemplo: 1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Materiales encontrados correctamente" ),
            @ApiResponse(responseCode = "404", description = "No se encontraron resultados"),
            @ApiResponse(responseCode = "500", description = "Error interno en servidor")})

    @GetMapping("/ciudad/{ciudadId}")
    public ResponseEntity<ApiResponseDTO<List<MaterialResponse>>> buscarPorCiudad(
            @PathVariable Long ciudadId) {

        List<MaterialResponse> materiales = materialService.buscarPorCiudad(ciudadId);

        return ResponseEntity.ok(
                ApiResponseDTO.<List<MaterialResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message(Constants.MATERIAL_ENCONTRADO)
                        .data(materiales)
                        .build()
        );
    }

    /**
     * Actualiza la información de un material existente.
     *
     * <p>
     * Antes de realizar la actualización se validan las reglas
     * de negocio necesarias, como la existencia del material,
     * existencia de la ciudad asociada y posibles duplicados.
     * </p>
     *
     * @param id identificador único del material a actualizar
     * @param request información actualizada del material
     * @return información del material actualizado
     *
     * @throws MaterialNotFoundException cuando el material no existe
     * @throws CiudadNotFoundException cuando la ciudad asociada no existe
     * @throws MaterialAlreadyExistsException cuando existe otro material
     *         con el mismo nombre y descripción
     */
    @Operation(summary = "Actualizar un material", description = "Permite actualizar un material, debes ingresar el id del material mas la nueva informacion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Material actualizado correctamente" ),
            @ApiResponse(responseCode = "400", description = "Datos inválidos enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno en servidor")})

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<MaterialResponse>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody MaterialRequest request) {

        MaterialResponse material = materialService.actualizar(id, request);

        return ResponseEntity.ok(
                ApiResponseDTO.<MaterialResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message(Constants.MATERIAL_ACTUALIZADO)
                        .data(material)
                        .build()
        );
    }

}