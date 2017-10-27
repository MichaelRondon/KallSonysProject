package edu.puj.aes.pica.asperisk.service.mapper;

import edu.puj.aes.pica.asperisk.domain.*;
import edu.puj.aes.pica.asperisk.service.dto.ClienteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Cliente and its DTO ClienteDTO.
 */
@Mapper(componentModel = "spring", uses = {TarjetaMapper.class, })
public interface ClienteMapper extends EntityMapper <ClienteDTO, Cliente> {

    @Mapping(source = "datosTarjeta.id", target = "datosTarjetaId")
    ClienteDTO toDto(Cliente cliente); 

    @Mapping(source = "datosTarjetaId", target = "datosTarjeta")
    Cliente toEntity(ClienteDTO clienteDTO); 
    default Cliente fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cliente cliente = new Cliente();
        cliente.setId(id);
        return cliente;
    }
}
