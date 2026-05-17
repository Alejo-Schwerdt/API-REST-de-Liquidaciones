package com.transportista.liquidaciones.dto.request;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class PagoRequestDTO {

    private Long liquidacionId;
    @NotNull
    private double monto;
    @NotBlank
    private String metodoPago; // Ejemplo: Transferencia, Efectivo, etc.
    private String observaciones;
}
