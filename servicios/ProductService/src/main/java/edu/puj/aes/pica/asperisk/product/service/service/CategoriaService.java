package edu.puj.aes.pica.asperisk.product.service.service;

//import edu.puj.aes.pica.asperisk.domain.Categoria;
//import edu.puj.aes.pica.asperisk.repository.CategoriaRepository;
//import edu.puj.aes.pica.asperisk.service.dto.CategoriaDTO;
//import edu.puj.aes.pica.asperisk.service.mapper.CategoriaMapper;
import edu.puj.aes.pica.asperisk.oms.utilities.dto.CategoriaDTO;
import edu.puj.aes.pica.asperisk.product.jpa.service.repository.CategoriaRepository;
import edu.puj.aes.pica.asperisk.product.service.jpa.entity.Categoria;
import edu.puj.aes.pica.asperisk.product.service.mapper.CategoriaMapper;
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

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaMapper categoriaMapper;

//    public CategoriaService(CategoriaRepository categoriaRepository, CategoriaMapper categoriaMapper) {
//        this.categoriaRepository = categoriaRepository;
//        this.categoriaMapper = categoriaMapper;
//    }
    /**
     * Save a categoria.
     *
     * @param categoriaDTO the entity to save
     * @return the persisted entity
     */
    public CategoriaDTO save(CategoriaDTO categoriaDTO) {
        log.info("Request to save Categoria : {}", categoriaDTO);
        Categoria categoria = categoriaMapper.toEntity(categoriaDTO);
        categoria = categoriaRepository.save(categoria);
        return categoriaMapper.toDto(categoria);
    }

    /**
     * Get all the categorias.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CategoriaDTO> findAll(Pageable pageable) {
        log.info("Request to get all Categorias Sort:{}", pageable.getSort());
//        PageRequest pageRequest = new PageRequest(pageable.getPageNumber(),
//                pageable.getPageSize(), new Sort(pageable.getSort().toString().replace("id:%20ASC: ASC", "categoria: ASC")));

        return categoriaRepository.findAll(pageable)
                .map(categoriaMapper::toDto);
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
        Categoria categoria = categoriaRepository.findOne(id);
        return categoriaMapper.toDto(categoria);
    }

    /**
     * Delete the categoria by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Categoria : {}", id);
        categoriaRepository.delete(id);
    }
}
