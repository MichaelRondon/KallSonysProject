package edu.puj.aes.pica.asperisk.service.mapper;

import edu.puj.aes.pica.asperisk.domain.*;
import edu.puj.aes.pica.asperisk.service.dto.CampaniaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Campania and its DTO CampaniaDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductoMapper.class, })
public interface CampaniaMapper extends EntityMapper <CampaniaDTO, Campania> {
    
    
    default Campania fromId(Long id) {
        if (id == null) {
            return null;
        }
        Campania campania = new Campania();
        campania.setId(id);
        return campania;
    }
}
