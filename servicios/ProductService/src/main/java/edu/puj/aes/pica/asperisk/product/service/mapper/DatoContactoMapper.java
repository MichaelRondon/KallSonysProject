package edu.puj.aes.pica.asperisk.product.service.mapper;

import edu.puj.aes.pica.asperisk.oms.utilities.dto.DatoContactoDTO;
import edu.puj.aes.pica.asperisk.product.service.jpa.entity.DatoContacto;

import org.mapstruct.*;

/**
 * Mapper for the entity DatoContacto and its DTO DatoContactoDTO.
 */
@Mapper(componentModel = "spring", uses = {ProveedorMapper.class, })
public interface DatoContactoMapper extends EntityMapper <DatoContactoDTO, DatoContacto> {

    @Mapping(source = "proveedor.id", target = "proveedorId")
    @Override
    DatoContactoDTO toDto(DatoContacto datoContacto); 

    @Mapping(source = "proveedorId", target = "proveedor")
    DatoContacto toEntity(DatoContactoDTO datoContactoDTO); 
    default DatoContacto fromId(Long id) {
        if (id == null) {
            return null;
        }
        DatoContacto datoContacto = new DatoContacto();
        datoContacto.setId(id);
        return datoContacto;
    }
}
