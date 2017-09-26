package edu.puj.aes.pica.asperisk.product.service.mapper;

import edu.puj.aes.pica.asperisk.oms.utilities.dto.ProductoDTO;
import edu.puj.aes.pica.asperisk.oms.utilities.model.Product;
import edu.puj.aes.pica.asperisk.product.service.jpa.entity.Producto;


/**
 * Mapper for the entity Producto and its DTO ProductoDTO.
 */
//@Mapper(componentModel = "spring", uses = {CategoriaMapper.class, })
public interface ProductoMapper extends EntityMapper <Product, Producto> {

//    @Mapping(source = "categoria.id", target = "categoriaId")
//    @Mapping(source = "categoria.categoria", target = "categoriaCategoria")
//    @Mapping(target = "keyWords", ignore = true)
//    @Mapping(target = "categoria", ignore = true)
//    @Mapping(target = "proveedores", ignore = true)
    @Override
    Product toDto(Producto producto);
//    @Mapping(target = "proveedores", ignore = true)
//    @Mapping(target = "keyWords", ignore = true)
//    @Mapping(target = "categoria", ignore = true)

//    @Mapping(source = "categoriaId", target = "categoria")
    @Override
    Producto toEntity(Product productoDTO);
    default Producto fromId(Long id) {
        if (id == null) {
            return null;
        }
        Producto producto = new Producto();
        producto.setId(id);
        return producto;
    }
}
