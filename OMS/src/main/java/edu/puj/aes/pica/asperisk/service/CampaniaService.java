package edu.puj.aes.pica.asperisk.service;

import edu.puj.aes.pica.asperisk.domain.Campania;
import edu.puj.aes.pica.asperisk.repository.CampaniaRepository;
import edu.puj.aes.pica.asperisk.oms.utilities.dto.CampaniaDTO;
import edu.puj.aes.pica.asperisk.product.service.client.CategoriaServiceRestClient;
import edu.puj.aes.pica.asperisk.service.mapper.CampaniaMapper;
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
    public CampaniaDTO save(CampaniaDTO campaniaDTO) {
        log.debug("Request to save Campania : {}", campaniaDTO);
        Campania campania = campaniaMapper.toEntity(campaniaDTO);
        campania = campaniaRepository.save(campania);
        return campaniaMapper.toDto(campania);
    }

    /**
     *  Get all the campanias.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CampaniaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Campanias");
        return campaniaRepository.findAll(pageable)
            .map(campaniaMapper::toDto);
    }

    /**
     *  Get one campania by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public CampaniaDTO findOne(Long id) {
        log.debug("Request to get Campania : {}", id);
        Campania campania = campaniaRepository.findOneWithEagerRelationships(id);
        return campaniaMapper.toDto(campania);
    }

    /**
     *  Delete the  campania by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Campania : {}", id);
        campaniaRepository.delete(id);
    }
}
