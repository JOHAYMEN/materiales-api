package com.prueba.materialesapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.materialesapi.dto.request.MaterialRequest;
import com.prueba.materialesapi.dto.response.MaterialResponse;
import com.prueba.materialesapi.enums.EstadoMaterial;
import com.prueba.materialesapi.exception.ControllerAdvisorExc;
import com.prueba.materialesapi.exception.MaterialNotFoundException;
import com.prueba.materialesapi.service.MaterialService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MaterialController.class)
@Import(ControllerAdvisorExc.class)
class MaterialControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MaterialService materialService;

    @Autowired
    private ObjectMapper objectMapper;

    private MaterialRequest request;
    private MaterialResponse response;

    @BeforeEach
    void setUp(){

        request = MaterialRequest.builder()
                .nombre("Laptop")
                .descripcion("Lenovo")
                .tipo("Tecnologia")
                .precio(BigDecimal.valueOf(2500))
                .fechaCompra(LocalDate.now())
                .estado(EstadoMaterial.ACTIVO)
                .ciudadId(1L)
                .build();

        response = MaterialResponse.builder()
                .id(1L)
                .nombre("Laptop")
                .descripcion("Lenovo")
                .tipo("Tecnologia")
                .build();
    }

    @Test
    void debeObtenerTodosLosMateriales() throws Exception {

        when(materialService.obtenerTodos())
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/materiales"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message")
                        .value("Materiales encontrados correctamente"))
                .andExpect(jsonPath("$.data[0].nombre")
                        .value("Laptop"));

        verify(materialService).obtenerTodos();
    }

    @Test
    void debeRetornar404CuandoNoHayMateriales() throws Exception {

        when(materialService.obtenerTodos())
                .thenThrow(new MaterialNotFoundException("Material no encontrado"));

        mockMvc.perform(get("/api/materiales"))
                .andExpect(status().isNotFound());

        verify(materialService).obtenerTodos();
    }

    @Test
    void debeBuscarPorTipo() throws Exception {

        when(materialService.buscarPorTipo("Tecnologia"))
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/materiales/tipo/Tecnologia"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].nombre")
                        .value("Laptop"));

        verify(materialService).buscarPorTipo("Tecnologia");
    }

    @Test
    void debeGuardarMaterial() throws Exception {

        when(materialService.guardar(any(MaterialRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/materiales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(201))
                .andExpect(jsonPath("$.data.nombre")
                        .value("Laptop"));

        verify(materialService).guardar(any(MaterialRequest.class));
    }

    @Test
    void debeRetornar400CuandoRequestEsInvalido() throws Exception {

        request.setNombre("");

        mockMvc.perform(post("/api/materiales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(materialService, never()).guardar(any());
    }

    @Test
    void debeActualizarMaterial() throws Exception {

        when(materialService.actualizar(eq(1L), any(MaterialRequest.class)))
                .thenReturn(response);

        mockMvc.perform(put("/api/materiales/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.data.nombre")
                        .value("Laptop"));

        verify(materialService).actualizar(eq(1L), any(MaterialRequest.class));
    }

    @Test
    void debeBuscarPorCiudad() throws Exception {

        when(materialService.buscarPorCiudad(1L))
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/materiales/ciudad/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].nombre")
                        .value("Laptop"));

        verify(materialService).buscarPorCiudad(1L);
    }

    @Test
    void debeBuscarPorFechaCompra() throws Exception {

        when(materialService.buscarPorFechaCompra(LocalDate.of(2026,7,24)))
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/materiales/fecha-compra/2026-07-24"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].nombre")
                        .value("Laptop"));

        verify(materialService)
                .buscarPorFechaCompra(LocalDate.of(2026,7,24));
    }

}
