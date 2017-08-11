package edu.puj.aes.pica.asperisk.service.mapper;

import edu.puj.aes.pica.asperisk.domain.*;
import edu.puj.aes.pica.asperisk.service.dto.ProveedorProductoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProveedorProducto and its DTO ProveedorProductoDTO.
 */
@Mapper(componentModel = "spring", uses = {ProveedorMapper.class, ProductoMapper.class, })
public interface ProveedorProductoMapper extends EntityMapper <ProveedorProductoDTO, ProveedorProducto> {

    @Mapping(source = "proveedor.id", target = "proveedorId")

    @Mapping(source = "producto.id", target = "productoId")
    ProveedorProductoDTO toDto(ProveedorProducto proveedorProducto); 

    @Mapping(source = "proveedorId", target = "proveedor")

    @Mapping(source = "productoId", target = "producto")
    ProveedorProducto toEntity(ProveedorProductoDTO proveedorProductoDTO); 
    default ProveedorProducto fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProveedorProducto proveedorProducto = new ProveedorProducto();
        proveedorProducto.setId(id);
        return proveedorProducto;
    }
}
