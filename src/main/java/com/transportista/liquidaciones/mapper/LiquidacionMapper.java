package com.transportista.liquidaciones.mapper;

import com.transportista.liquidaciones.dto.request.LiquidacionRequestDTO;
import com.transportista.liquidaciones.dto.response.LiquidacionResponseDTO;
import com.transportista.liquidaciones.entity.Liquidacion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {
        LiquidacionDetalleMapper.class,
        PagoMapper.class
})
public interface LiquidacionMapper {

    // RequestDTO -> Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "detalles", ignore = true)
    @Mapping(target = "pago", ignore = true)
    @Mapping(target = "totalRetencion", ignore = true)
    @Mapping(target = "totalDescuentos", ignore = true)
    @Mapping(target = "totalNeto", ignore = true)
    @Mapping(target = "estado", ignore = true)
    Liquidacion toEntity(LiquidacionRequestDTO dto);

    // Entity -> ResponseDTO
    LiquidacionResponseDTO toDto(Liquidacion entity);

    // List<Entity> -> List<ResponseDTO>
    List<LiquidacionResponseDTO> toDtoList(List<Liquidacion> entities);
}
