package com.transportista.liquidaciones.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.transportista.liquidaciones.dto.request.LiquidacionRequestDTO;
import com.transportista.liquidaciones.dto.response.LiquidacionResponseDTO;
import com.transportista.liquidaciones.service.LiquidacionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/liquidaciones")
@RequiredArgsConstructor
public class LiquidacionController {

    private final LiquidacionService liquidacionService;

    // CREAR LIQUIDACIÓN
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LiquidacionResponseDTO crear(
            @Valid @RequestBody LiquidacionRequestDTO request) {

        return liquidacionService.crearLiquidacion(request);
    }

    // OBTENER LIQUIDACIÓN POR ID
    @GetMapping("/{id}")
    public LiquidacionResponseDTO obtenerPorId(
            @PathVariable Long id) {

        return liquidacionService.obtenerPorId(id);
    }

    // LISTAR TODAS LAS LIQUIDACIONES
    @GetMapping
    public List<LiquidacionResponseDTO> listarTodas() {
        return liquidacionService.listarTodas();
    }

    // ELIMINAR LIQUIDACIÓN
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(
            @PathVariable Long id) {

        liquidacionService.eliminar(id);
    }

    // MARCAR LIQUIDACIÓN COMO PAGADA
    @PutMapping("/{id}/pagar")
    public LiquidacionResponseDTO marcarComoPagada(
            @PathVariable Long id) {

        return liquidacionService.marcarComoPagada(id);
    }

    // ANULAR LIQUIDACIÓN
    @PutMapping("/{id}/anular")
    public LiquidacionResponseDTO anular(
            @PathVariable Long id) {

        return liquidacionService.anularLiquidacion(id);
    }
    // ACTUALIZAR LIQUIDACIÓN
    @PutMapping("/{id}")
    public LiquidacionResponseDTO actualizar(
            @PathVariable Long id,
            @Valid @RequestBody LiquidacionRequestDTO request) {
    
        return liquidacionService.actualizarLiquidacion(id, request);
    }
}