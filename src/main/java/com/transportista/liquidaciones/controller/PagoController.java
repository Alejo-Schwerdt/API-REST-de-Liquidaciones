package com.transportista.liquidaciones.controller;

import com.transportista.liquidaciones.dto.request.PagoRequestDTO;
import com.transportista.liquidaciones.dto.response.PagoResponseDTO;
import com.transportista.liquidaciones.service.PagoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;

    //Crear un nuevo pago.
    @PostMapping
    public PagoResponseDTO crear(
            @Valid @RequestBody PagoRequestDTO request) {

        return pagoService.crearPago(request);
    }


    // Obtener un pago por ID.

    @GetMapping("/{id}")
    public PagoResponseDTO obtenerPorId(
            @PathVariable Long id) {

        return pagoService.obtenerPorId(id);
    }
    //Listar todos los pagos.

    @GetMapping
    public List<PagoResponseDTO> listarTodos() {

        return pagoService.listarTodos();
    }

    /**
     * Eliminar un pago.
     * Al eliminarlo, la liquidación vuelve a estado PENDIENTE.
     */
    @DeleteMapping("/{id}")
    public void eliminar(
            @PathVariable Long id) {

        pagoService.eliminar(id);
    }
}