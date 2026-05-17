package com.transportista.liquidaciones.controller;

import com.transportista.liquidaciones.dto.request.LiquidacionDetalleRequestDTO;
import com.transportista.liquidaciones.dto.response.LiquidacionDetalleResponseDTO;
import com.transportista.liquidaciones.service.LiquidacionDetalleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/liquidacion-detalles")
@RequiredArgsConstructor
public class LiquidacionDetalleController {

    private final LiquidacionDetalleService liquidacionDetalleService;

    
    //Crear un nuevo detalle de liquidación.
    @PostMapping
    public LiquidacionDetalleResponseDTO crear(
            @Valid @RequestBody LiquidacionDetalleRequestDTO request) {

        return liquidacionDetalleService.crear(request);
    }


    //Obtener un detalle por su ID.

    @GetMapping("/{id}")
    public LiquidacionDetalleResponseDTO obtenerPorId(
            @PathVariable Long id) {

        return liquidacionDetalleService.obtenerPorId(id);
    }

    //Obtener todos los detalles de una liquidación.
    
    @GetMapping("/liquidacion/{liquidacionId}")
    public List<LiquidacionDetalleResponseDTO> obtenerPorLiquidacion(
            @PathVariable Long liquidacionId) {

        return liquidacionDetalleService.obtenerPorLiquidacion(liquidacionId);
    }


    // Eliminar un detalle por ID.

    @DeleteMapping("/{id}")
    public void eliminar(
            @PathVariable Long id) {

        liquidacionDetalleService.eliminar(id);
    }
}