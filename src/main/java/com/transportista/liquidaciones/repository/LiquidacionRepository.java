package com.transportista.liquidaciones.repository;

import com.transportista.liquidaciones.entity.Liquidacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LiquidacionRepository extends JpaRepository<Liquidacion, Long> {
    List<Liquidacion> findByCamioneroId(Long camioneroId);
    List<Liquidacion> findByEmpresaId(Long empresaId);
    List<Liquidacion> findByEstado(String estado);
}