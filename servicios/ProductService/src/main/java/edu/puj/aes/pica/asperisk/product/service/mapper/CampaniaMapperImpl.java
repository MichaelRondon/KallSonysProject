package edu.puj.aes.pica.asperisk.product.service.mapper;

import edu.puj.aes.pica.asperisk.oms.utilities.model.Campanign;
import edu.puj.aes.pica.asperisk.product.service.jpa.entity.Campania;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author acost
 */
@Service
public class CampaniaMapperImpl implements CampaniaMapper {

    @Autowired
    private ProductoMapper productoMapper;

    @Override
    public Campania toEntity(Campanign dto) {
        Campania campania = new Campania();
        campania.setCategoria(dto.getCategoria());
        campania.setDescripcion(dto.getDescripcion());
        campania.setEstado(dto.getEstado());
        campania.setFechaFin(dto.getFechaFin());
        campania.setFechaInicio(dto.getFechaInicio());
        campania.setId(dto.getId());
        campania.setNombre(dto.getNombre());

        campania.setProductos(dto.getProductos().parallelStream().map(productoMapper::toEntity).collect(Collectors.toSet()));
        return campania;
    }

    @Override
    public Campanign toDto(Campania entity) {

        Campanign campaniaDTO = new Campanign();
        campaniaDTO.setCategoria(entity.getCategoria());
        campaniaDTO.setDescripcion(entity.getDescripcion());
        campaniaDTO.setEstado(entity.getEstado());
        campaniaDTO.setFechaFin(entity.getFechaFin());
        campaniaDTO.setFechaInicio(entity.getFechaInicio());
        campaniaDTO.setId(entity.getId());
        campaniaDTO.setNombre(entity.getNombre());
        campaniaDTO.setProductos(entity.getProductos().parallelStream().map(productoMapper::toDto).collect(Collectors.toList()));
        return campaniaDTO;
    }

    @Override
    public List<Campania> toEntity(List<Campanign> dtoList) {
        return dtoList.parallelStream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public List<Campanign> toDto(List<Campania> entityList) {
        return entityList.parallelStream().map(this::toDto).collect(Collectors.toList());
    }

}
