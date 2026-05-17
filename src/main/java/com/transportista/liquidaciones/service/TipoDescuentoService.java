package com.transportista.liquidaciones.service;

import com.transportista.liquidaciones.dto.request.TipoDescuentoRequestDTO;
import com.transportista.liquidaciones.dto.response.TipoDescuentoResponseDTO;
import com.transportista.liquidaciones.entity.TipoDescuento;
import com.transportista.liquidaciones.mapper.TipoDescuentoMapper;
import com.transportista.liquidaciones.repository.TipoDescuentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoDescuentoService {

    private final TipoDescuentoRepository tipoDescuentoRepository;
    private final TipoDescuentoMapper tipoDescuentoMapper;


    //Crear un nuevo tipo de descuento.

    public TipoDescuentoResponseDTO crear(TipoDescuentoRequestDTO request) {

        // Validar que no exista otro tipo con el mismo nombre
        if (tipoDescuentoRepository.existsByNombreIgnoreCase(request.getNombre())) {
            throw new RuntimeException("Ya existe un tipo de descuento con ese nombre");
        }

        // Convertir DTO a entidad
        TipoDescuento tipoDescuento = tipoDescuentoMapper.toEntity(request);

        // Guardar en base de datos
        TipoDescuento guardado = tipoDescuentoRepository.save(tipoDescuento);

        // Convertir a DTO de respuesta
        return tipoDescuentoMapper.toDto(guardado);
    }


    //Obtener un tipo de descuento por ID.

    public TipoDescuentoResponseDTO obtenerPorId(Long id) {
        TipoDescuento tipoDescuento = tipoDescuentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de descuento no encontrado"));

        return tipoDescuentoMapper.toDto(tipoDescuento);
    }


    //Obtener todos los tipos de descuento.
    public List<TipoDescuentoResponseDTO> obtenerTodos() {
        List<TipoDescuento> tipos = tipoDescuentoRepository.findAll();
        return tipoDescuentoMapper.toDtoList(tipos);
    }


    //Actualizar un tipo de descuento existente.

    public TipoDescuentoResponseDTO actualizar(Long id, TipoDescuentoRequestDTO request) {

        TipoDescuento tipoDescuento = tipoDescuentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de descuento no encontrado"));

        // Validar nombre duplicado (si cambió)
        if (!tipoDescuento.getNombre().equalsIgnoreCase(request.getNombre())
                && tipoDescuentoRepository.existsByNombreIgnoreCase(request.getNombre())) {
            throw new RuntimeException("Ya existe un tipo de descuento con ese nombre");
        }

        // Actualizar campos
        tipoDescuento.setNombre(request.getNombre());
        tipoDescuento.setDescripcion(request.getDescripcion());

        // Guardar cambios
        TipoDescuento actualizado = tipoDescuentoRepository.save(tipoDescuento);

        return tipoDescuentoMapper.toDto(actualizado);
    }

  
    //Eliminar un tipo de descuento.
 
    public void eliminar(Long id) {

        if (!tipoDescuentoRepository.existsById(id)) {
            throw new RuntimeException("Tipo de descuento no encontrado");
        }

        tipoDescuentoRepository.deleteById(id);
    }
}
