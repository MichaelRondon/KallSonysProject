package edu.puj.aes.pica.asperisk.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.puj.aes.pica.asperisk.domain.DatoContacto;

import edu.puj.aes.pica.asperisk.repository.DatoContactoRepository;
import edu.puj.aes.pica.asperisk.web.rest.util.HeaderUtil;
import edu.puj.aes.pica.asperisk.service.dto.DatoContactoDTO;
import edu.puj.aes.pica.asperisk.service.mapper.DatoContactoMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DatoContacto.
 */
@RestController
@RequestMapping("/api")
public class DatoContactoResource {

    private final Logger log = LoggerFactory.getLogger(DatoContactoResource.class);

    private static final String ENTITY_NAME = "datoContacto";

    private final DatoContactoRepository datoContactoRepository;

    private final DatoContactoMapper datoContactoMapper;

    public DatoContactoResource(DatoContactoRepository datoContactoRepository, DatoContactoMapper datoContactoMapper) {
        this.datoContactoRepository = datoContactoRepository;
        this.datoContactoMapper = datoContactoMapper;
    }

    /**
     * POST  /dato-contactos : Create a new datoContacto.
     *
     * @param datoContactoDTO the datoContactoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new datoContactoDTO, or with status 400 (Bad Request) if the datoContacto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dato-contactos")
    @Timed
    public ResponseEntity<DatoContactoDTO> createDatoContacto(@RequestBody DatoContactoDTO datoContactoDTO) throws URISyntaxException {
        log.debug("REST request to save DatoContacto : {}", datoContactoDTO);
        if (datoContactoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new datoContacto cannot already have an ID")).body(null);
        }
        DatoContacto datoContacto = datoContactoMapper.toEntity(datoContactoDTO);
        datoContacto = datoContactoRepository.save(datoContacto);
        DatoContactoDTO result = datoContactoMapper.toDto(datoContacto);
        return ResponseEntity.created(new URI("/api/dato-contactos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dato-contactos : Updates an existing datoContacto.
     *
     * @param datoContactoDTO the datoContactoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated datoContactoDTO,
     * or with status 400 (Bad Request) if the datoContactoDTO is not valid,
     * or with status 500 (Internal Server Error) if the datoContactoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dato-contactos")
    @Timed
    public ResponseEntity<DatoContactoDTO> updateDatoContacto(@RequestBody DatoContactoDTO datoContactoDTO) throws URISyntaxException {
        log.debug("REST request to update DatoContacto : {}", datoContactoDTO);
        if (datoContactoDTO.getId() == null) {
            return createDatoContacto(datoContactoDTO);
        }
        DatoContacto datoContacto = datoContactoMapper.toEntity(datoContactoDTO);
        datoContacto = datoContactoRepository.save(datoContacto);
        DatoContactoDTO result = datoContactoMapper.toDto(datoContacto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, datoContactoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dato-contactos : get all the datoContactos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of datoContactos in body
     */
    @GetMapping("/dato-contactos")
    @Timed
    public List<DatoContactoDTO> getAllDatoContactos() {
        log.debug("REST request to get all DatoContactos");
        List<DatoContacto> datoContactos = datoContactoRepository.findAll();
        return datoContactoMapper.toDto(datoContactos);
    }

    /**
     * GET  /dato-contactos/:id : get the "id" datoContacto.
     *
     * @param id the id of the datoContactoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the datoContactoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/dato-contactos/{id}")
    @Timed
    public ResponseEntity<DatoContactoDTO> getDatoContacto(@PathVariable Long id) {
        log.debug("REST request to get DatoContacto : {}", id);
        DatoContacto datoContacto = datoContactoRepository.findOne(id);
        DatoContactoDTO datoContactoDTO = datoContactoMapper.toDto(datoContacto);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(datoContactoDTO));
    }

    /**
     * DELETE  /dato-contactos/:id : delete the "id" datoContacto.
     *
     * @param id the id of the datoContactoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dato-contactos/{id}")
    @Timed
    public ResponseEntity<Void> deleteDatoContacto(@PathVariable Long id) {
        log.debug("REST request to delete DatoContacto : {}", id);
        datoContactoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
