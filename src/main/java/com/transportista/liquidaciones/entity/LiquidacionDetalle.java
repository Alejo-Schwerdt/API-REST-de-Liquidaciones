package com.transportista.liquidaciones.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.Id;

@Entity
@Table(name = "liquidacion_detalles")
@Data
public class LiquidacionDetalle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "liquidacion_id")
    private Liquidacion liquidacion;
    @ManyToOne
    @JoinColumn(name = "tipo_descuento_id")
    private TipoDescuento tipoDescuento;
    private String descripcion;
    private Double monto;
    private String tipo; // Ingreso o Descuento

}
