package edu.puj.aes.pica.asperisk.service.mapper;

import edu.puj.aes.pica.asperisk.domain.*;
import edu.puj.aes.pica.asperisk.oms.utilities.dto.ProveedorDTO;
import edu.puj.aes.pica.asperisk.service.mapper.EntityMapper;

import org.mapstruct.*;

/**
 * Mapper for the entity Proveedor and its DTO ProveedorDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProveedorMapper extends EntityMapper <ProveedorDTO, Proveedor> {
    
    @Mapping(target = "datosContactos", ignore = true)
    @Mapping(target = "productos", ignore = true)
    Proveedor toEntity(ProveedorDTO proveedorDTO); 
    default Proveedor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Proveedor proveedor = new Proveedor();
        proveedor.setId(id);
        return proveedor;
    }
}
