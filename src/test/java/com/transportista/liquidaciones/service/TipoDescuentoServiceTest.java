package com.transportista.liquidaciones.service;

import com.transportista.liquidaciones.dto.request.TipoDescuentoRequestDTO;
import com.transportista.liquidaciones.dto.response.TipoDescuentoResponseDTO;
import com.transportista.liquidaciones.entity.TipoDescuento;
import com.transportista.liquidaciones.mapper.TipoDescuentoMapper;
import com.transportista.liquidaciones.repository.TipoDescuentoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TipoDescuentoServiceTest {

    @Mock
    private TipoDescuentoRepository tipoDescuentoRepository;

    @Mock
    private TipoDescuentoMapper tipoDescuentoMapper;

    @InjectMocks
    private TipoDescuentoService tipoDescuentoService;

    @Test
    void debeCrearTipoDescuentoCorrectamente() {
        // Arrange
        TipoDescuentoRequestDTO request = new TipoDescuentoRequestDTO();
        request.setNombre("Combustible");
        request.setDescripcion("Descuento por combustible");

        TipoDescuento entidad = new TipoDescuento();
        entidad.setNombre("Combustible");
        entidad.setDescripcion("Descuento por combustible");

        TipoDescuento guardado = new TipoDescuento();
        guardado.setId(1L);
        guardado.setNombre("Combustible");
        guardado.setDescripcion("Descuento por combustible");

        TipoDescuentoResponseDTO response = new TipoDescuentoResponseDTO();
        response.setId(1L);
        response.setNombre("Combustible");
        response.setDescripcion("Descuento por combustible");

        when(tipoDescuentoRepository.existsByNombreIgnoreCase("Combustible"))
                .thenReturn(false);

        when(tipoDescuentoMapper.toEntity(request))
                .thenReturn(entidad);

        when(tipoDescuentoRepository.save(entidad))
                .thenReturn(guardado);

        when(tipoDescuentoMapper.toDto(guardado))
                .thenReturn(response);

        // Act
        TipoDescuentoResponseDTO resultado =
                tipoDescuentoService.crear(request);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Combustible", resultado.getNombre());

        verify(tipoDescuentoRepository)
                .existsByNombreIgnoreCase("Combustible");
        verify(tipoDescuentoRepository)
                .save(entidad);
    }

    @Test
    void debeLanzarExcepcionSiYaExisteElNombre() {
        // Arrange
        TipoDescuentoRequestDTO request = new TipoDescuentoRequestDTO();
        request.setNombre("Combustible");

        when(tipoDescuentoRepository.existsByNombreIgnoreCase("Combustible"))
                .thenReturn(true);

        // Act + Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> tipoDescuentoService.crear(request)
        );

        assertEquals(
                "Ya existe un tipo de descuento con ese nombre",
                exception.getMessage()
        );

        verify(tipoDescuentoRepository, never()).save(any());
    }
}
