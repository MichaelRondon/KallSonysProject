package edu.puj.aes.pica.asperisk.product.service.mapper;

import edu.puj.aes.pica.asperisk.oms.utilities.dto.ProveedorProductoDTO;
import edu.puj.aes.pica.asperisk.product.service.jpa.entity.ProveedorProducto;

import org.mapstruct.*;

/**
 * Mapper for the entity ProveedorProducto and its DTO ProveedorProductoDTO.
 */
@Mapper(componentModel = "spring", uses = {ProveedorMapper.class, ProductoMapper.class, })
public interface ProveedorProductoMapper extends EntityMapper <ProveedorProductoDTO, ProveedorProducto> {

    @Mapping(source = "proveedor.id", target = "proveedorId")

    @Mapping(source = "producto.id", target = "productoId")
    @Override
    ProveedorProductoDTO toDto(ProveedorProducto proveedorProducto); 

    @Mapping(source = "proveedorId", target = "proveedor")

    @Mapping(source = "productoId", target = "producto")
    @Override
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
