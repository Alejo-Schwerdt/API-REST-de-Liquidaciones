package com.transportista.liquidaciones.dto.request;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
@Data
public class TipoDescuentoRequestDTO {
    @NotBlank
    private String nombre;
    private String descripcion;
}
