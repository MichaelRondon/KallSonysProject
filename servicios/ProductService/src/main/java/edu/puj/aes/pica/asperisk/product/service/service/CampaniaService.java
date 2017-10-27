package edu.puj.aes.pica.asperisk.product.service.service;

import edu.puj.aes.pica.asperisk.oms.utilities.model.Campanign;
import edu.puj.aes.pica.asperisk.oms.utilities.model.Categoria;
import edu.puj.aes.pica.asperisk.product.jpa.service.repository.CampaniaRepository;
import edu.puj.aes.pica.asperisk.product.service.jpa.entity.Campania;
import edu.puj.aes.pica.asperisk.product.service.mapper.CampaniaMapper;
import edu.puj.aes.pica.asperisk.product.service.model.CampaignRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing Campania.
 */
@Service
@Transactional
public class CampaniaService {

    private final Logger log = LoggerFactory.getLogger(CampaniaService.class);

    private final CampaniaRepository campaniaRepository;

    @Autowired
    private final CampaniaMapper campaniaMapper;

    public CampaniaService(CampaniaRepository campaniaRepository, CampaniaMapper campaniaMapper) {
        this.campaniaRepository = campaniaRepository;
        this.campaniaMapper = campaniaMapper;
    }

    /**
     * Save a campania.
     *
     * @param campaniaDTO the entity to save
     * @return the persisted entity
     */
    public Campanign save(Campanign campaniaDTO) {
        log.debug("Request to save Campania : {}", campaniaDTO);
        Campania campania = campaniaMapper.toEntity(campaniaDTO);
        campania = campaniaRepository.save(campania);
        return campaniaMapper.toDto(campania);
    }

    /**
     * Get all the campanias.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Campanign> findAll(Pageable pageable) {
        log.debug("Request to get all Campanias");
        return campaniaRepository.findAll(pageable)
                .map(campaniaMapper::toDto);
    }

    public Page<Campanign> findAll(Pageable pageable, CampaignRequest campaniasRequest) {
        log.debug("Request to get all Campanias CampaignRequest: {}", campaniasRequest);
        if (campaniasRequest.getCategoria() != null
                && campaniasRequest.getState() != null) {
            return campaniaRepository.findAllByEstadoAndCategoria(pageable, campaniasRequest.getState(), campaniasRequest.getCategoria())
                    .map(campaniaMapper::toDto);
        }
        if (campaniasRequest.getCategoria() != null) {
            return campaniaRepository.findAllByCategoria(pageable, campaniasRequest.getCategoria())
                    .map(campaniaMapper::toDto);            
        }
        if (campaniasRequest.getState() != null) {
            return campaniaRepository.findAllByEstado(pageable, campaniasRequest.getState())
                    .map(campaniaMapper::toDto);            
        }
        return campaniaRepository.findAll(pageable)
                .map(campaniaMapper::toDto);
    }

    /**
     * Get one campania by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Campanign findOne(Long id) {
        log.info("Request to get Campania : {}", id);
        Campania campania = campaniaRepository.findOne(id);
        if (campania == null) {
            return null;
        }
//        Campania campania = campaniaRepository.findOneWithEagerRelationships(id);
        return campaniaMapper.toDto(campania);
    }

    /**
     * Delete the campania by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Campania : {}", id);
        campaniaRepository.delete(id);
    }
}
