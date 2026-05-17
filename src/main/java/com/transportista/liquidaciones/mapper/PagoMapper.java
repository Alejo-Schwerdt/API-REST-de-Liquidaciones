package com.transportista.liquidaciones.mapper;

import com.transportista.liquidaciones.dto.request.PagoRequestDTO;
import com.transportista.liquidaciones.dto.response.PagoResponseDTO;
import com.transportista.liquidaciones.entity.Pago;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PagoMapper {

    // RequestDTO -> Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaPago", ignore = true)
    @Mapping(target = "liquidacion", ignore = true)
    Pago toEntity(PagoRequestDTO dto);

    // Entity -> ResponseDTO
    @Mapping(target = "liquidacionId", source = "liquidacion.id")
    PagoResponseDTO toDto(Pago entity);

    // List<Entity> -> List<ResponseDTO>
    List<PagoResponseDTO> toDtoList(List<Pago> entities);
}