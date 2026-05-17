package com.transportista.liquidaciones.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.transportista.liquidaciones.dto.request.LiquidacionRequestDTO;
import com.transportista.liquidaciones.dto.response.LiquidacionResponseDTO;
import com.transportista.liquidaciones.entity.Liquidacion;
import com.transportista.liquidaciones.entity.LiquidacionDetalle;
import com.transportista.liquidaciones.mapper.LiquidacionMapper;
import com.transportista.liquidaciones.repository.LiquidacionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LiquidacionService {

    private final LiquidacionRepository liquidacionRepository;
    private final LiquidacionMapper liquidacionMapper;

    // CREAR LIQUIDACIÓN
    public LiquidacionResponseDTO crearLiquidacion(LiquidacionRequestDTO request) {

        // 1. Convertir DTO a entidad
        Liquidacion liquidacion = liquidacionMapper.toEntity(request);

        // 2. Calcular retención
        double totalRetencion =
                liquidacion.getTotalBruto()
                * liquidacion.getPorcentajeRetencion()
                / 100;

        // 3. Calcular descuentos
        double totalDescuentos =
                calcularTotalDescuentos(liquidacion.getDetalles());

        // 4. Calcular neto
        double totalNeto =
                liquidacion.getTotalBruto()
                - totalRetencion
                - totalDescuentos;

        // 5. Asignar valores calculados
        liquidacion.setTotalRetencion(totalRetencion);
        liquidacion.setTotalDescuentos(totalDescuentos);
        liquidacion.setTotalNeto(totalNeto);

        // 6. Estado inicial
        liquidacion.setEstado("PENDIENTE");

        // 7. Guardar
        Liquidacion saved =
                liquidacionRepository.save(liquidacion);

        // 8. Retornar DTO
        return liquidacionMapper.toDto(saved);
    }
    // OBTENER POR ID
    public LiquidacionResponseDTO obtenerPorId(Long id) {
        Liquidacion liquidacion =
                liquidacionRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Liquidación no encontrada con ID: " + id));

        return liquidacionMapper.toDto(liquidacion);
    }

    // LISTAR TODAS
    public List<LiquidacionResponseDTO> listarTodas() {
        return liquidacionRepository.findAll()
                .stream()
                .map(liquidacionMapper::toDto)
                .collect(Collectors.toList());
    }


    // ELIMINAR
    public void eliminar(Long id) {
        if (!liquidacionRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    "Liquidación no encontrada con ID: " + id);
        }

        liquidacionRepository.deleteById(id);
    }

    // MARCAR COMO PAGADA
    public LiquidacionResponseDTO marcarComoPagada(Long id) {
        Liquidacion liquidacion =
                liquidacionRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Liquidación no encontrada con ID: " + id));

        liquidacion.setEstado("PAGADA");

        Liquidacion updated =
                liquidacionRepository.save(liquidacion);

        return liquidacionMapper.toDto(updated);
    }
    // ANULAR
    public LiquidacionResponseDTO anularLiquidacion(Long id) {
        Liquidacion liquidacion =
                liquidacionRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Liquidación no encontrada con ID: " + id));

        liquidacion.setEstado("ANULADA");

        Liquidacion updated =
                liquidacionRepository.save(liquidacion);

        return liquidacionMapper.toDto(updated);
    }
    // CALCULAR DESCUENTOS
    private double calcularTotalDescuentos(
            List<LiquidacionDetalle> detalles) {

        if (detalles == null || detalles.isEmpty()) {
            return 0.0;
        }

        return detalles.stream()
                .mapToDouble(LiquidacionDetalle::getMonto)
                .sum();
    }
    // ACTUALIZAR LIQUIDACION 
    public LiquidacionResponseDTO actualizarLiquidacion(
            Long id,
            LiquidacionRequestDTO request) {

        // 1. Buscar liquidación existente
        Liquidacion liquidacion = liquidacionRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Liquidación no encontrada con ID: " + id));

        // 2. Validar estado
        if (!"PENDIENTE".equalsIgnoreCase(liquidacion.getEstado())) {
            throw new IllegalStateException(
                    "Solo se pueden actualizar liquidaciones en estado PENDIENTE");
        }

        // 3. Actualizar campos editables
        liquidacion.setCamioneroId(request.getCamioneroId());
        liquidacion.setEmpresaId(request.getEmpresaId());
        liquidacion.setMes(request.getMes());
        liquidacion.setAnio(request.getAnio());
        liquidacion.setTotalBruto(request.getTotalBruto());
        liquidacion.setPorcentajeRetencion(request.getPorcentajeRetencion());

        // 4. Recalcular retención
        double totalRetencion =
                liquidacion.getTotalBruto()
                * liquidacion.getPorcentajeRetencion()
                / 100;

        // 5. Recalcular descuentos
        double totalDescuentos =
                calcularTotalDescuentos(liquidacion.getDetalles());

        // 6. Recalcular neto
        double totalNeto =
                liquidacion.getTotalBruto()
                - totalRetencion
                - totalDescuentos;

        // 7. Asignar nuevos valores
        liquidacion.setTotalRetencion(totalRetencion);
        liquidacion.setTotalDescuentos(totalDescuentos);
        liquidacion.setTotalNeto(totalNeto);

        // 8. Guardar cambios
        Liquidacion updated = liquidacionRepository.save(liquidacion);

        // 9. Retornar DTO
        return liquidacionMapper.toDto(updated);
    }
}