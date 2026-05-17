package com.transportista.liquidaciones.repository;

import com.transportista.liquidaciones.entity.TipoDescuento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TipoDescuentoRepository extends JpaRepository<TipoDescuento, Long> {
    Optional<TipoDescuento> findByNombre(String nombre);
    boolean existsByNombreIgnoreCase(String nombre);
}
