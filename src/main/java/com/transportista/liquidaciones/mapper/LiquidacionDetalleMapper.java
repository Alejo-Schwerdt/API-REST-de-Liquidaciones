package com.transportista.liquidaciones.mapper;

import com.transportista.liquidaciones.dto.request.LiquidacionDetalleRequestDTO;
import com.transportista.liquidaciones.dto.response.LiquidacionDetalleResponseDTO;
import com.transportista.liquidaciones.entity.LiquidacionDetalle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LiquidacionDetalleMapper {

    // RequestDTO -> Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "liquidacion", ignore = true)
    @Mapping(target = "tipoDescuento", ignore = true)
    LiquidacionDetalle toEntity(LiquidacionDetalleRequestDTO dto);

    // Entity -> ResponseDTO
    @Mapping(
        target = "tipoDescuentoNombre",
        expression = "java(entity.getTipoDescuento() != null ? entity.getTipoDescuento().getNombre() : null)"
    )
    LiquidacionDetalleResponseDTO toDto(LiquidacionDetalle entity);

    // List<Entity> -> List<ResponseDTO>
    List<LiquidacionDetalleResponseDTO> toDtoList(List<LiquidacionDetalle> entities);
}