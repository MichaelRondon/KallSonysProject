package edu.puj.aes.pica.asperisk.service;

import edu.puj.aes.pica.asperisk.oms.utilities.model.Campanign;
import edu.puj.aes.pica.asperisk.product.service.client.CampaniaServiceRestClient;
import edu.puj.aes.pica.asperisk.repository.CampaniaRepository;
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

//    private final CampaniaMapper campaniaMapper;
    @Autowired
    private CampaniaServiceRestClient campaniaServiceRestClient;

    public CampaniaService(CampaniaRepository campaniaRepository) {
        this.campaniaRepository = campaniaRepository;
//        this.campaniaMapper = campaniaMapper;
    }

    /**
     * Save a campania.
     *
     * @param campaniaDTO the entity to save
     * @return the persisted entity
     */
    public Campanign save(Campanign campaniaDTO) {
        log.debug("Request to save Campania : {}", campaniaDTO);
//        Campania campania = campaniaMapper.toEntity(campaniaDTO);
//        campania = campaniaRepository.save(campania);
//        return campaniaMapper.toDto(campania);
        return campaniaServiceRestClient.save(campaniaDTO);
    }

    /**
     * Save a campania.
     *
     * @param campaniaDTO the entity to save
     * @return the persisted entity
     */
    public Campanign update(Campanign campaniaDTO) {
        log.debug("Request to update Campania : {}", campaniaDTO);
//        Campania campania = campaniaMapper.toEntity(campaniaDTO);
//        campania = campaniaRepository.save(campania);
//        return campaniaMapper.toDto(campania);
        return campaniaServiceRestClient.update(campaniaDTO);
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
        return campaniaServiceRestClient.findAll(pageable);
//        return campaniaRepository.findAll(pageable)
//            .map(campaniaMapper::toDto);
    }

    /**
     * Get one campania by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Campanign findOne(Long id) {
        log.debug("Request to get Campania : {}", id);
//        Campania campania = campaniaRepository.findOneWithEagerRelationships(id);
//        return campaniaMapper.toDto(campania);
        return campaniaServiceRestClient.findOne(id);
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
