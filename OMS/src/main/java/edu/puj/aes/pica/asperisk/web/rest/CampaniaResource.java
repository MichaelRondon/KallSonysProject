package edu.puj.aes.pica.asperisk.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.puj.aes.pica.asperisk.service.CampaniaService;
import edu.puj.aes.pica.asperisk.oms.utilities.rest.util.HeaderUtil;
import edu.puj.aes.pica.asperisk.oms.utilities.rest.util.PaginationUtil;
import edu.puj.aes.pica.asperisk.oms.utilities.model.Campanign;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Campania.
 */
@RestController
@RequestMapping("/api")
public class CampaniaResource {

    private final Logger log = LoggerFactory.getLogger(CampaniaResource.class);

    private static final String ENTITY_NAME = "campania";

    private final CampaniaService campaniaService;

    public CampaniaResource(CampaniaService campaniaService) {
        this.campaniaService = campaniaService;
    }

    /**
     * POST  /campanias : Create a new campania.
     *
     * @param campaniaDTO the campaniaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new campaniaDTO, or with status 400 (Bad Request) if the campania has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/campanias")
    @Timed
    public ResponseEntity<Campanign> createCampania(@RequestBody Campanign campaniaDTO) throws URISyntaxException {
        log.info("REST request to save Campania : {}", campaniaDTO);
        if (campaniaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new campania cannot already have an ID")).body(null);
        }
        Campanign result = campaniaService.save(campaniaDTO);
        return ResponseEntity.created(new URI("/api/campanias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /campanias : Updates an existing campania.
     *
     * @param campaniaDTO the campaniaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated campaniaDTO,
     * or with status 400 (Bad Request) if the campaniaDTO is not valid,
     * or with status 500 (Internal Server Error) if the campaniaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/campanias")
    @Timed
    public ResponseEntity<Campanign> updateCampania(@RequestBody Campanign campaniaDTO) throws URISyntaxException {
        log.debug("REST request to update Campania : {}", campaniaDTO);
        if (campaniaDTO.getId() == null) {
            return createCampania(campaniaDTO);
        }
        Campanign result = campaniaService.update(campaniaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, campaniaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /campanias : get all the campanias.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of campanias in body
     */
    @GetMapping("/campanias")
    @Timed
    public ResponseEntity<List<Campanign>> getAllCampanias(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Campanias");
        Page<Campanign> page = campaniaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/campanias");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /campanias/:id : get the "id" campania.
     *
     * @param id the id of the campaniaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the campaniaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/campanias/{id}")
    @Timed
    public ResponseEntity<Campanign> getCampania(@PathVariable Long id) {
        log.debug("REST request to get Campania : {}", id);
        Campanign campaniaDTO = campaniaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(campaniaDTO));
    }

    /**
     * DELETE  /campanias/:id : delete the "id" campania.
     *
     * @param id the id of the campaniaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/campanias/{id}")
    @Timed
    public ResponseEntity<Void> deleteCampania(@PathVariable Long id) {
        log.debug("REST request to delete Campania : {}", id);
        campaniaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
