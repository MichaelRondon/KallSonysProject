package edu.puj.aes.pica.asperisk.service.mapper;

import edu.puj.aes.pica.asperisk.domain.*;
import edu.puj.aes.pica.asperisk.service.dto.ProductoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Producto and its DTO ProductoDTO.
 */
@Mapper(componentModel = "spring", uses = {CategoriaMapper.class, })
public interface ProductoMapper extends EntityMapper <ProductoDTO, Producto> {

//    @Mapping(source = "categoria.id", target = "categoriaId")
//    @Mapping(source = "categoria.categoria", target = "categoriaCategoria")
    @Mapping(target = "keyWords", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    @Mapping(target = "proveedores", ignore = true)
    ProductoDTO toDto(Producto producto);
    @Mapping(target = "proveedores", ignore = true)
    @Mapping(target = "keyWords", ignore = true)
    @Mapping(target = "categoria", ignore = true)

//    @Mapping(source = "categoriaId", target = "categoria")
    Producto toEntity(ProductoDTO productoDTO);
    default Producto fromId(Long id) {
        if (id == null) {
            return null;
        }
        Producto producto = new Producto();
        producto.setId(id);
        return producto;
    }
}
