package com.transportista.liquidaciones.repository;

import com.transportista.liquidaciones.entity.LiquidacionDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LiquidacionDetalleRepository extends JpaRepository<LiquidacionDetalle, Long> {
    List<LiquidacionDetalle> findByLiquidacionId(Long liquidacionId);
}
