package edu.puj.aes.pica.asperisk.service;

import edu.puj.aes.pica.asperisk.domain.ProveedorProducto;
import edu.puj.aes.pica.asperisk.repository.ProveedorProductoRepository;
import edu.puj.aes.pica.asperisk.oms.utilities.dto.ProveedorProductoDTO;
import edu.puj.aes.pica.asperisk.service.mapper.ProveedorProductoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ProveedorProducto.
 */
@Service
@Transactional
public class ProveedorProductoService {

    private final Logger log = LoggerFactory.getLogger(ProveedorProductoService.class);

    private final ProveedorProductoRepository proveedorProductoRepository;

    private final ProveedorProductoMapper proveedorProductoMapper;

    public ProveedorProductoService(ProveedorProductoRepository proveedorProductoRepository, ProveedorProductoMapper proveedorProductoMapper) {
        this.proveedorProductoRepository = proveedorProductoRepository;
        this.proveedorProductoMapper = proveedorProductoMapper;
    }

    /**
     * Save a proveedorProducto.
     *
     * @param proveedorProductoDTO the entity to save
     * @return the persisted entity
     */
    public ProveedorProductoDTO save(ProveedorProductoDTO proveedorProductoDTO) {
        log.debug("Request to save ProveedorProducto : {}", proveedorProductoDTO);
        ProveedorProducto proveedorProducto = proveedorProductoMapper.toEntity(proveedorProductoDTO);
        proveedorProducto = proveedorProductoRepository.save(proveedorProducto);
        return proveedorProductoMapper.toDto(proveedorProducto);
    }

    /**
     *  Get all the proveedorProductos.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ProveedorProductoDTO> findAll() {
        log.debug("Request to get all ProveedorProductos");
        return proveedorProductoRepository.findAll().stream()
            .map(proveedorProductoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one proveedorProducto by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ProveedorProductoDTO findOne(Long id) {
        log.debug("Request to get ProveedorProducto : {}", id);
        ProveedorProducto proveedorProducto = proveedorProductoRepository.findOne(id);
        return proveedorProductoMapper.toDto(proveedorProducto);
    }

    /**
     *  Delete the  proveedorProducto by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ProveedorProducto : {}", id);
        proveedorProductoRepository.delete(id);
    }
}
