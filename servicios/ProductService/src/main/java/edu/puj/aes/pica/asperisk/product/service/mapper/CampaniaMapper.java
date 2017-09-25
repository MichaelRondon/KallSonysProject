package edu.puj.aes.pica.asperisk.product.service.mapper;


import edu.puj.aes.pica.asperisk.oms.utilities.dto.CampaniaDTO;
import edu.puj.aes.pica.asperisk.product.service.jpa.entity.Campania;
import org.mapstruct.Mapper;


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
