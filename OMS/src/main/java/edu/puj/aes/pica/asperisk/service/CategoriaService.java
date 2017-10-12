package edu.puj.aes.pica.asperisk.service;

import edu.puj.aes.pica.asperisk.domain.Categoria;
import edu.puj.aes.pica.asperisk.repository.CategoriaRepository;
import edu.puj.aes.pica.asperisk.oms.utilities.dto.CategoriaDTO;
import edu.puj.aes.pica.asperisk.product.service.client.CategoriaServiceRestClient;
import edu.puj.aes.pica.asperisk.service.mapper.CategoriaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing Categoria.
 */
@Service
@Transactional
public class CategoriaService {

    private final Logger log = LoggerFactory.getLogger(CategoriaService.class);

    private final CategoriaRepository categoriaRepository;

//    private final CategoriaMapper categoriaMapper;
    @Autowired
    private CategoriaServiceRestClient categoriaServiceRestClient;

    public CategoriaService(CategoriaRepository categoriaRepository/*, CategoriaMapper categoriaMapper*/) {
        this.categoriaRepository = categoriaRepository;
//        this.categoriaMapper = categoriaMapper;
    }

    /**
     * Save a categoria.
     *
     * @param categoriaDTO the entity to save
     * @return the persisted entity
     */
    public CategoriaDTO save(CategoriaDTO categoriaDTO) {
        log.debug("Request to save Categoria : {}", categoriaDTO);
//        Categoria categoria = categoriaMapper.toEntity(categoriaDTO);
//        categoria = categoriaRepository.save(categoria);
//        return categoriaMapper.toDto(categoria);
        return categoriaServiceRestClient.save(categoriaDTO);
    }

    /**
     * Save a categoria.
     *
     * @param categoriaDTO the entity to save
     * @return the persisted entity
     */
    public CategoriaDTO update(CategoriaDTO categoriaDTO) {
        log.debug("Request to save Categoria : {}", categoriaDTO);
//        Categoria categoria = categoriaMapper.toEntity(categoriaDTO);
//        categoria = categoriaRepository.save(categoria);
//        return categoriaMapper.toDto(categoria);
        return categoriaServiceRestClient.update(categoriaDTO);
    }

    /**
     * Get all the categorias.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CategoriaDTO> findAll(Pageable pageable) {
        log.info("Request to get all Categorias: {}",pageable.getSort());
//        return categoriaRepository.findAll(pageable)
//                .map(categoriaMapper::toDto);
        return categoriaServiceRestClient.findAll(pageable);
    }

    /**
     * Get one categoria by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public CategoriaDTO findOne(Long id) {
        log.debug("Request to get Categoria : {}", id);
//        Categoria categoria = categoriaRepository.findOne(id);
//        return categoriaMapper.toDto(categoria);
        return categoriaServiceRestClient.findOne(id);
    }

    /**
     * Delete the categoria by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Categoria : {}", id);
        categoriaServiceRestClient.delete(id);
    }
}
