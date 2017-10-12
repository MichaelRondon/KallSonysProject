package edu.puj.aes.pica.asperisk.product.service.mapper;

import edu.puj.aes.pica.asperisk.oms.utilities.dto.CategoriaDTO;
import edu.puj.aes.pica.asperisk.product.service.jpa.entity.Categoria;

//import org.mapstruct.*;

/**
 * Mapper for the entity Categoria and its DTO CategoriaDTO.
 */
//@Mapper(componentModel = "spring", uses = {})
public interface CategoriaMapper extends EntityMapper <CategoriaDTO, Categoria> {
    
    
    default Categoria fromId(Long id) {
        if (id == null) {
            return null;
        }
        Categoria categoria = new Categoria();
        categoria.setId(id);
        return categoria;
    }
}
