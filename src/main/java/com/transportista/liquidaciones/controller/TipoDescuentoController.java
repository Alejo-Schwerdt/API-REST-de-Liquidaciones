package com.transportista.liquidaciones.controller;

import com.transportista.liquidaciones.dto.request.TipoDescuentoRequestDTO;
import com.transportista.liquidaciones.dto.response.TipoDescuentoResponseDTO;
import com.transportista.liquidaciones.service.TipoDescuentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-descuento")
@RequiredArgsConstructor
public class TipoDescuentoController {

    private final TipoDescuentoService tipoDescuentoService;
    
    //Crear un nuevo tipo de descuento.
    @PostMapping
    public TipoDescuentoResponseDTO crear(
            @Valid @RequestBody TipoDescuentoRequestDTO request) {

        return tipoDescuentoService.crear(request);
    }
    //Obtener un tipo de descuento por ID.
    @GetMapping("/{id}")
    public TipoDescuentoResponseDTO obtenerPorId(
            @PathVariable Long id) {

        return tipoDescuentoService.obtenerPorId(id);
    }
    //Obtener todos los tipos de descuento.
    @GetMapping
    public List<TipoDescuentoResponseDTO> obtenerTodos() {

        return tipoDescuentoService.obtenerTodos();
    }
    //Actualizar un tipo de descuento existente.
    @PutMapping("/{id}")
    public TipoDescuentoResponseDTO actualizar(
            @PathVariable Long id,
            @Valid @RequestBody TipoDescuentoRequestDTO request) {

        return tipoDescuentoService.actualizar(id, request);
    }
    //Eliminar un tipo de descuento.
    @DeleteMapping("/{id}")
    public void eliminar(
            @PathVariable Long id) {

        tipoDescuentoService.eliminar(id);
    }
}