package com.transportista.liquidaciones.entity;

import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.CascadeType;
import java.util.List;

@Entity
@Table(name = "liquidaciones")
@Data
public class Liquidacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @OneToMany(mappedBy = "liquidacion", cascade = CascadeType.ALL)
    private List<LiquidacionDetalle> detalles = new ArrayList<>();
    @OneToOne(mappedBy = "liquidacion")
    private Pago pago;

}