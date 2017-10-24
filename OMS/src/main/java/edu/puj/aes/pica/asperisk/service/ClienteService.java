package edu.puj.aes.pica.asperisk.service;

import edu.puj.aes.pica.asperisk.domain.Cliente;
import edu.puj.aes.pica.asperisk.repository.ClienteRepository;
import edu.puj.aes.pica.asperisk.service.dto.ClienteDTO;
import edu.puj.aes.pica.asperisk.service.mapper.ClienteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Cliente.
 */
@Service
@Transactional
public class ClienteService {

    private final Logger log = LoggerFactory.getLogger(ClienteService.class);

    private final ClienteRepository clienteRepository;

    private final ClienteMapper clienteMapper;

    public ClienteService(ClienteRepository clienteRepository, ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    /**
     * Save a cliente.
     *
     * @param clienteDTO the entity to save
     * @return the persisted entity
     */
    public ClienteDTO save(ClienteDTO clienteDTO) {
        log.debug("Request to save Cliente : {}", clienteDTO);
        Cliente cliente = clienteMapper.toEntity(clienteDTO);
        cliente = clienteRepository.save(cliente);
        return clienteMapper.toDto(cliente);
    }

    /**
     *  Get all the clientes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ClienteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Clientes");
        return clienteRepository.findAll(pageable)
            .map(clienteMapper::toDto);
    }

    /**
     *  Get one cliente by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ClienteDTO findOne(Long id) {
        log.debug("Request to get Cliente : {}", id);
        Cliente cliente = clienteRepository.findOne(id);
        return clienteMapper.toDto(cliente);
    }

    /**
     *  Delete the  cliente by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Cliente : {}", id);
        clienteRepository.delete(id);
    }
}
