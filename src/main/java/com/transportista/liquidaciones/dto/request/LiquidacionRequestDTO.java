package com.transportista.liquidaciones.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LiquidacionRequestDTO {
    private Long camioneroId;
    private Long empresaId;
    @NotBlank
    private String mes;
    private int anio;
    private double totalBruto;
    private double porcentajeRetencion;
}
