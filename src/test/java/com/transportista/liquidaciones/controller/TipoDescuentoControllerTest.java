package com.transportista.liquidaciones.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transportista.liquidaciones.dto.request.TipoDescuentoRequestDTO;
import com.transportista.liquidaciones.dto.response.TipoDescuentoResponseDTO;
import com.transportista.liquidaciones.security.JwtAuthFilter;
import com.transportista.liquidaciones.security.JwtService;
import com.transportista.liquidaciones.service.TipoDescuentoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TipoDescuentoController.class)
@AutoConfigureMockMvc(addFilters = false)
class TipoDescuentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TipoDescuentoService tipoDescuentoService;

    // Mocks de seguridad
    @MockBean
    private JwtService jwtService;

    @MockBean
    private JwtAuthFilter jwtAuthFilter;

    @Test
    void debeCrearTipoDescuento() throws Exception {

        TipoDescuentoRequestDTO request = new TipoDescuentoRequestDTO();
        request.setNombre("Combustible");
        request.setDescripcion("Descuento por combustible");

        TipoDescuentoResponseDTO response = new TipoDescuentoResponseDTO();
        response.setId(1L);
        response.setNombre("Combustible");
        response.setDescripcion("Descuento por combustible");

        when(tipoDescuentoService.crear(any(TipoDescuentoRequestDTO.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/tipos-descuento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Combustible"));
    }
}