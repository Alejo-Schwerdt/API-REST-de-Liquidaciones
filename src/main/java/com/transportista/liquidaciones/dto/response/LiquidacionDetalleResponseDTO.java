package com.transportista.liquidaciones.dto.response;
import lombok.Data;

@Data
public class LiquidacionDetalleResponseDTO {
    private Long id;
    private String tipo; // Ingreso o Descuento
    private String descripcion;
    private Double monto;
    private String tipoDescuentoNombre; // Solo para detalles de tipo Descuento

}
