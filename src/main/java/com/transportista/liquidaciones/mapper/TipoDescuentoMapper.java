package com.transportista.liquidaciones.mapper;

import com.transportista.liquidaciones.dto.request.TipoDescuentoRequestDTO;
import com.transportista.liquidaciones.dto.response.TipoDescuentoResponseDTO;
import com.transportista.liquidaciones.entity.TipoDescuento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TipoDescuentoMapper {

    // RequestDTO -> Entity
    @Mapping(target = "id", ignore = true)    
    TipoDescuento toEntity(TipoDescuentoRequestDTO dto);

    // Entity -> ResponseDTO
    TipoDescuentoResponseDTO toDto(TipoDescuento entity);

    // List<Entity> -> List<ResponseDTO>
    List<TipoDescuentoResponseDTO> toDtoList(List<TipoDescuento> entities);
}