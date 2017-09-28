package edu.puj.aes.pica.asperisk.product.service.rest;

import edu.puj.aes.pica.asperisk.oms.utilities.model.Campanign;
import edu.puj.aes.pica.asperisk.oms.utilities.model.Product;
import edu.puj.aes.pica.asperisk.oms.utilities.rest.util.HeaderUtil;
import edu.puj.aes.pica.asperisk.oms.utilities.rest.util.PaginationUtil;
import edu.puj.aes.pica.asperisk.product.service.exceptions.ProductTransactionException;
import edu.puj.aes.pica.asperisk.product.service.service.CampaniaService;
import edu.puj.aes.pica.asperisk.product.service.service.ProductService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * REST controller for managing Campania.
 */
@RestController
@RequestMapping("/api")
public class CampaniaResource {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CampaniaResource.class);
    
    private static final String ENTITY_NAME = "campania";
    
    private final CampaniaService campaniaService;
    
    @Autowired
    @Qualifier("elastic")
    private ProductService elasticSearchService;
    
    public CampaniaResource(CampaniaService campaniaService) {
        this.campaniaService = campaniaService;
    }

    /**
     * POST /campanias : Create a new campania.
     *
     * @param campaniaDTO the campaniaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new campaniaDTO, or with status 400 (Bad Request) if the campania has
     * already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/campanias")
    public ResponseEntity<Campanign> createCampania(@RequestBody Campanign campaniaDTO) throws URISyntaxException {
        LOGGER.debug("REST request to save Campania : {}", campaniaDTO);
        if (campaniaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new campania cannot already have an ID")).body(null);
        }
        Campanign result = campaniaService.save(campaniaDTO);
        return ResponseEntity.created(new URI("/api/campanias/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT /campanias : Updates an existing campania.
     *
     * @param campaniaDTO the campaniaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     * campaniaDTO, or with status 400 (Bad Request) if the campaniaDTO is not
     * valid, or with status 500 (Internal Server Error) if the campaniaDTO
     * couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/campanias")
    public ResponseEntity<Campanign> updateCampania(@RequestBody Campanign campaniaDTO) throws URISyntaxException {
        LOGGER.debug("REST request to update Campania : {}", campaniaDTO);
        if (campaniaDTO.getId() == null) {
            return createCampania(campaniaDTO);
        }
        Campanign result = campaniaService.save(campaniaDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, campaniaDTO.getId().toString()))
                .body(result);
    }

    /**
     * GET /campanias : get all the campanias.
     *
     * @param page
     * @param sort
     * @param size
     * @return the ResponseEntity with status 200 (OK) and the list of campanias
     * in body
     */
    @GetMapping("/campanias")
    public ResponseEntity<List<Campanign>> getAllCampanias(
            @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
            @RequestParam(value = "sort", defaultValue = "", required = false) String sort,
            @RequestParam(value = "size", defaultValue = "1", required = false) Integer size) {
        LOGGER.debug("REST request to get a page of Campanias");
        Page<Campanign> response = campaniaService.findAll(PaginationUtil.getPageRequest(page, sort, size));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(response, "/api/campanias");
        return new ResponseEntity<>(response.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /campanias/:id : get the "id" campania.
     *
     * @param id the id of the campaniaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * campaniaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/campanias/{id}")
    public ResponseEntity<Campanign> getCampania(@PathVariable Long id) {
        LOGGER.debug("REST request to get Campania : {}", id);
        Campanign campaniaDTO = campaniaService.findOne(id);
        List<Long> collect = campaniaDTO.getProductos().stream().map(Product::getId).collect(Collectors.toList());
        try {
            List<Product> findAllByIds = elasticSearchService.findAllByIds(collect);
            campaniaDTO.setProductos(findAllByIds);
        } catch (ProductTransactionException ex) {
            LOGGER.error("Error buscando los productos para la lista de ids: {}", collect);
        }
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(campaniaDTO));
    }

    /**
     * DELETE /campanias/:id : delete the "id" campania.
     *
     * @param id the id of the campaniaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/campanias/{id}")
    public ResponseEntity<Void> deleteCampania(@PathVariable Long id) {
        LOGGER.debug("REST request to delete Campania : {}", id);
        campaniaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
