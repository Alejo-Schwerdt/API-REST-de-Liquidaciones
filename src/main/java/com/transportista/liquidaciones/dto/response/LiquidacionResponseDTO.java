package com.transportista.liquidaciones.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class LiquidacionResponseDTO {
    private Long id;
    private Long camioneroId;
    private Long empresaId;
    private String mes;
    private int anio;
    private Double totalBruto;
    private Double porcentajeRetencion;
    private Double totalRetencion;
    private Double totalDescuentos;
    private Double totalNeto;
    private String estado; // Pendiente, Pagada, Anulada
    private List<LiquidacionDetalleResponseDTO> detalles; // Detalles asociados a la liquidación
    private PagoResponseDTO pago; // Información del pago asociado (si existe)
}
