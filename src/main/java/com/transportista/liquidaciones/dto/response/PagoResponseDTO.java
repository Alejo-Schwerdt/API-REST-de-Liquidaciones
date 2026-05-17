package com.transportista.liquidaciones.dto.response;

import lombok.Data;

@Data
public class PagoResponseDTO {

    private Long id;
    private Long liquidacionId;
    private Double monto; 
    private String metodoPago;
    private String observaciones;
}
