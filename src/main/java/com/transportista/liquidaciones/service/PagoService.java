package com.transportista.liquidaciones.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.transportista.liquidaciones.dto.request.PagoRequestDTO;
import com.transportista.liquidaciones.dto.response.PagoResponseDTO;
import com.transportista.liquidaciones.entity.Liquidacion;
import com.transportista.liquidaciones.entity.Pago;
import com.transportista.liquidaciones.mapper.PagoMapper;
import com.transportista.liquidaciones.repository.LiquidacionRepository;
import com.transportista.liquidaciones.repository.PagoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PagoService {

    private final PagoRepository pagoRepository;
    private final LiquidacionRepository liquidacionRepository;
    private final PagoMapper pagoMapper;

    // CREAR PAGO
    public PagoResponseDTO crearPago(PagoRequestDTO request) {

        // 1. Buscar la liquidación
        Liquidacion liquidacion = liquidacionRepository.findById(
                request.getLiquidacionId())
            .orElseThrow(() ->
                new IllegalArgumentException(
                    "Liquidación no encontrada con ID: "
                    + request.getLiquidacionId()));

        // 2. Validar que no tenga pago
        if (liquidacion.getPago() != null) {
            throw new IllegalArgumentException(
                "La liquidación ya posee un pago registrado.");
        }

        // 3. Crear entidad Pago
        Pago pago = new Pago();
        pago.setFechaPago(LocalDate.now());
        pago.setMonto(request.getMonto());
        pago.setMetodoPago(request.getMetodoPago());
        pago.setObservaciones(request.getObservaciones());
        pago.setLiquidacion(liquidacion);

        // 4. Actualizar liquidación
        liquidacion.setEstado("PAGADA");
        liquidacion.setPago(pago);

        // 5. Guardar
        Pago saved = pagoRepository.save(pago);

        // 6. Retornar DTO
        return pagoMapper.toDto(saved);
    }
    // OBTENER POR ID
    public PagoResponseDTO obtenerPorId(Long id) {
        Pago pago = pagoRepository.findById(id)
            .orElseThrow(() ->
                new IllegalArgumentException(
                    "Pago no encontrado con ID: " + id));

        return pagoMapper.toDto(pago);
    }
    // LISTAR TODOS
    public List<PagoResponseDTO> listarTodos() {
        return pagoRepository.findAll()
            .stream()
            .map(pagoMapper::toDto)
            .collect(Collectors.toList());
    }

    // ELIMINAR PAGO
    public void eliminar(Long id) {

        Pago pago = pagoRepository.findById(id)
            .orElseThrow(() ->
                new IllegalArgumentException(
                    "Pago no encontrado con ID: " + id));

        // Volver la liquidación a PENDIENTE
        Liquidacion liquidacion = pago.getLiquidacion();

        if (liquidacion != null) {
            liquidacion.setEstado("PENDIENTE");
            liquidacion.setPago(null);
        }

        pagoRepository.delete(pago);
    }
}
