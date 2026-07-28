package com.prueba.materialesapi.service.impl;

import com.prueba.materialesapi.dto.request.MaterialRequest;
import com.prueba.materialesapi.dto.response.MaterialResponse;
import com.prueba.materialesapi.entity.Ciudad;
import com.prueba.materialesapi.entity.Material;
import com.prueba.materialesapi.enums.EstadoMaterial;
import com.prueba.materialesapi.exception.MaterialDuplicadoException;
import com.prueba.materialesapi.exception.MaterialNotFoundException;
import com.prueba.materialesapi.exception.FechaCompraInvalidaException;
import com.prueba.materialesapi.mapper.MaterialMapper;
import com.prueba.materialesapi.repository.CiudadRepository;
import com.prueba.materialesapi.repository.MaterialRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MaterialServiceImplTest {

    @Mock
    private MaterialRepository materialRepository;

    @Mock
    private CiudadRepository ciudadRepository;

    @Mock
    private MaterialMapper materialMapper;

    @InjectMocks
    private MaterialServiceImpl service;

    private Material material;
    private MaterialResponse response;
    private MaterialRequest request;
    private Ciudad ciudad;

    @BeforeEach
    void setUp() {

        ciudad = Ciudad.builder()
                .id(1L)
                .nombre("Cartagena")
                .build();

        material = Material.builder()
                .id(1L)
                .nombre("Laptop")
                .descripcion("Lenovo")
                .tipo("Tecnologia")
                .precio(BigDecimal.valueOf(2000))
                .fechaCompra(LocalDate.now())
                .estado(EstadoMaterial.ACTIVO)
                .ciudad(ciudad)
                .build();

        response = MaterialResponse.builder()
                .id(1L)
                .nombre("Laptop")
                .build();

        request = MaterialRequest.builder()
                .nombre("Laptop")
                .descripcion("Lenovo")
                .tipo("Tecnologia")
                .precio(BigDecimal.valueOf(2000))
                .fechaCompra(LocalDate.now())
                .estado(EstadoMaterial.ACTIVO)
                .ciudadId(1L)
                .build();
    }

    @Test
    void debeRetornarTodosLosMateriales() {

        when(materialRepository.findAll())
                .thenReturn(List.of(material));

        when(materialMapper.toResponse(material))
                .thenReturn(response);

        List<MaterialResponse> resultado = service.obtenerTodos();

        assertEquals(1, resultado.size());

        verify(materialRepository).findAll();

        verify(materialMapper).toResponse(material);
    }

    @Test
    void debeLanzarExcepcionCuandoNoHayMateriales() {

        when(materialRepository.findAll())
                .thenReturn(List.of());

        assertThrows(
                MaterialNotFoundException.class,
                () -> service.obtenerTodos()
        );
    }

    @Test
    void debeBuscarMaterialPorTipo() {

        when(materialRepository.findByTipoIgnoreCase("Tecnologia"))
                .thenReturn(List.of(material));

        when(materialMapper.toResponse(material))
                .thenReturn(response);

        List<MaterialResponse> resultado =
                service.buscarPorTipo("Tecnologia");

        assertEquals(1, resultado.size());

        verify(materialRepository)
                .findByTipoIgnoreCase("Tecnologia");
    }

    @Test
    void debeLanzarExcepcionSiTipoNoExiste() {

        when(materialRepository.findByTipoIgnoreCase("X"))
                .thenReturn(List.of());

        assertThrows(
                MaterialNotFoundException.class,
                () -> service.buscarPorTipo("X")
        );
    }

    @Test
    void debeGuardarMaterialCorrectamente() {

        when(materialRepository
                .existsByNombreIgnoreCaseAndDescripcionIgnoreCase(any(), any()))
                .thenReturn(false);

        when(ciudadRepository.findById(1L))
                .thenReturn(Optional.of(ciudad));

        when(materialMapper.toEntity(request))
                .thenReturn(material);

        when(materialRepository.save(material))
                .thenReturn(material);

        when(materialMapper.toResponse(material))
                .thenReturn(response);

        MaterialResponse resultado = service.guardar(request);

        assertNotNull(resultado);

        verify(materialRepository).save(material);
    }

    @Test
    void debeLanzarExcepcionCuandoMaterialYaExiste() {

        when(materialRepository
                .existsByNombreIgnoreCaseAndDescripcionIgnoreCase(any(), any()))
                .thenReturn(true);

        assertThrows(
                MaterialDuplicadoException.class,
                () -> service.guardar(request)
        );

        verify(materialRepository, never()).save(any());
    }

    @Test
    void debeLanzarExcepcionCuandoFechaCompraEsMayor() {

        request.setFechaCompra(LocalDate.now());

        request.setFechaVenta(LocalDate.now().minusDays(1));

        assertThrows(
                FechaCompraInvalidaException.class,
                () -> service.guardar(request)
        );
    }

    @Test
    void debeLanzarExcepcionCuandoMaterialActualizarNoExiste() {

        when(materialRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                MaterialNotFoundException.class,
                () -> service.actualizar(1L, request)
        );
    }

    @Test
    void debeActualizarMaterialCorrectamente() {

        when(materialRepository
                .existsByNombreIgnoreCaseAndDescripcionIgnoreCaseAndIdNot(any(), any(), anyLong()))
                .thenReturn(false);

        when(materialRepository.findById(1L))
                .thenReturn(Optional.of(material));

        when(ciudadRepository.findById(1L))
                .thenReturn(Optional.of(ciudad));

        when(materialRepository.save(material))
                .thenReturn(material);

        when(materialMapper.toResponse(material))
                .thenReturn(response);

        MaterialResponse resultado = service.actualizar(1L, request);

        assertNotNull(resultado);

        verify(materialRepository).save(material);
    }

    @Test
    void debeLanzarExcepcionCuandoFechaEsInvalidaAlActualizar() {

        request.setFechaCompra(LocalDate.now());

        request.setFechaVenta(LocalDate.now().minusDays(1));

        assertThrows(
                FechaCompraInvalidaException.class,
                () -> service.actualizar(1L, request)
        );

        verify(materialRepository, never()).save(any());
    }

    @Test
    void debeBuscarMaterialPorFechaCompra() {

        when(materialRepository.findByFechaCompra(any(LocalDate.class)))
                .thenReturn(List.of(material));

        when(materialMapper.toResponse(material))
                .thenReturn(response);

        List<MaterialResponse> resultado =
                service.buscarPorFechaCompra(LocalDate.now());

        assertEquals(1, resultado.size());

        verify(materialRepository)
                .findByFechaCompra(any(LocalDate.class));
    }

    @Test
    void debeLanzarExcepcionCuandoNoHayMaterialesPorFecha() {

        when(materialRepository.findByFechaCompra(any(LocalDate.class)))
                .thenReturn(List.of());

        assertThrows(
                MaterialNotFoundException.class,
                () -> service.buscarPorFechaCompra(LocalDate.now())
        );
    }

    @Test
    void debeBuscarMaterialPorCiudad() {

        when(materialRepository.findByCiudadId(1L))
                .thenReturn(List.of(material));

        when(materialMapper.toResponse(material))
                .thenReturn(response);

        List<MaterialResponse> resultado =
                service.buscarPorCiudad(1L);

        assertEquals(1, resultado.size());

        verify(materialRepository).findByCiudadId(1L);
    }

    @Test
    void debeLanzarExcepcionCuandoNoHayMaterialesPorCiudad() {

        when(materialRepository.findByCiudadId(1L))
                .thenReturn(List.of());

        assertThrows(
                MaterialNotFoundException.class,
                () -> service.buscarPorCiudad(1L)
        );
    }

}
