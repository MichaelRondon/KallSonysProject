package edu.puj.aes.pica.asperisk.service.mapper;

import edu.puj.aes.pica.asperisk.domain.*;
import edu.puj.aes.pica.asperisk.service.dto.TarjetaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Tarjeta and its DTO TarjetaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TarjetaMapper extends EntityMapper <TarjetaDTO, Tarjeta> {
    
    
    default Tarjeta fromId(Long id) {
        if (id == null) {
            return null;
        }
        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setId(id);
        return tarjeta;
    }
}
