package com.transportista.liquidaciones.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LiquidacionDetalleRequestDTO {

    private long liquidacionId;
    private long tipoDescuentoId;
    private String descripcion;
    private double monto;
    @NotBlank
    private String tipo; // Ingreso o Descuento 
}
