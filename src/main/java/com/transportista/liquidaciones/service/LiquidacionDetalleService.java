package com.transportista.liquidaciones.service;

import com.transportista.liquidaciones.dto.request.LiquidacionDetalleRequestDTO;
import com.transportista.liquidaciones.dto.response.LiquidacionDetalleResponseDTO;
import com.transportista.liquidaciones.entity.Liquidacion;
import com.transportista.liquidaciones.entity.LiquidacionDetalle;
import com.transportista.liquidaciones.entity.TipoDescuento;
import com.transportista.liquidaciones.mapper.LiquidacionDetalleMapper;
import com.transportista.liquidaciones.repository.LiquidacionDetalleRepository;
import com.transportista.liquidaciones.repository.LiquidacionRepository;
import com.transportista.liquidaciones.repository.TipoDescuentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LiquidacionDetalleService {

    private final LiquidacionDetalleRepository liquidacionDetalleRepository;
    private final LiquidacionRepository liquidacionRepository;
    private final TipoDescuentoRepository tipoDescuentoRepository;
    private final LiquidacionDetalleMapper liquidacionDetalleMapper;

    /**
     * Crear un detalle de liquidación.
     * Puede ser de tipo "Ingreso" o "Descuento" Como un adelanto o el cargo del combustible.
     */
    public LiquidacionDetalleResponseDTO crear(LiquidacionDetalleRequestDTO request) {

        // 1. Buscar la liquidación asociada
        Liquidacion liquidacion = liquidacionRepository.findById(request.getLiquidacionId())
                .orElseThrow(() -> new RuntimeException("Liquidación no encontrada"));

        // 2. Crear el detalle
        LiquidacionDetalle detalle = new LiquidacionDetalle();
        detalle.setLiquidacion(liquidacion);
        detalle.setDescripcion(request.getDescripcion());
        detalle.setMonto(request.getMonto());
        detalle.setTipo(request.getTipo());

        // 3. Si es descuento, asociar TipoDescuento
        if ("Descuento".equalsIgnoreCase(request.getTipo())) {
            TipoDescuento tipoDescuento = tipoDescuentoRepository
                    .findById(request.getTipoDescuentoId())
                    .orElseThrow(() -> new RuntimeException("Tipo de descuento no encontrado"));

            detalle.setTipoDescuento(tipoDescuento);
        }

        // 4. Guardar el detalle
        LiquidacionDetalle detalleGuardado = liquidacionDetalleRepository.save(detalle);

        // 5. Recalcular totales de la liquidación
        recalcularTotales(liquidacion);

        // 6. Convertir a DTO y devolver
        return liquidacionDetalleMapper.toDto(detalleGuardado);
    }

    /**
     * Obtener un detalle por ID.
     */
    public LiquidacionDetalleResponseDTO obtenerPorId(Long id) {
        LiquidacionDetalle detalle = liquidacionDetalleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle no encontrado"));

        return liquidacionDetalleMapper.toDto(detalle);
    }

    /**
     * Obtener todos los detalles de una liquidación.
     */
    public List<LiquidacionDetalleResponseDTO> obtenerPorLiquidacion(Long liquidacionId) {
        List<LiquidacionDetalle> detalles =
                liquidacionDetalleRepository.findByLiquidacionId(liquidacionId);

        return liquidacionDetalleMapper.toDtoList(detalles);
    }

    /**
     * Eliminar un detalle.
     */
    public void eliminar(Long id) {
        LiquidacionDetalle detalle = liquidacionDetalleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle no encontrado"));

        Liquidacion liquidacion = detalle.getLiquidacion();

        liquidacionDetalleRepository.deleteById(id);

        // Recalcular totales luego de eliminar
        recalcularTotales(liquidacion);
    }

    /**
     * Recalcula los totales de la liquidación:
     * - totalDescuentos
     * - totalRetencion
     * - totalNeto
     */
    private void recalcularTotales(Liquidacion liquidacion) {

        List<LiquidacionDetalle> detalles =
                liquidacionDetalleRepository.findByLiquidacionId(liquidacion.getId());

        double totalDescuentos = 0.0;

        for (LiquidacionDetalle detalle : detalles) {
            if ("Descuento".equalsIgnoreCase(detalle.getTipo())) {
                totalDescuentos += detalle.getMonto();
            }
        }

        // Retención de la empresa
        double totalRetencion =
                liquidacion.getTotalBruto() * (liquidacion.getPorcentajeRetencion() / 100.0);

        // Neto final
        double totalNeto =
                liquidacion.getTotalBruto()
                        - totalRetencion
                        - totalDescuentos;

        liquidacion.setTotalDescuentos(totalDescuentos);
        liquidacion.setTotalRetencion(totalRetencion);
        liquidacion.setTotalNeto(totalNeto);

        liquidacionRepository.save(liquidacion);
    }
}