package edu.puj.aes.pica.asperisk.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.puj.aes.pica.asperisk.domain.Tarjeta;
import edu.puj.aes.pica.asperisk.oms.utilities.rest.util.HeaderUtil;

import edu.puj.aes.pica.asperisk.repository.TarjetaRepository;
import edu.puj.aes.pica.asperisk.service.dto.TarjetaDTO;
import edu.puj.aes.pica.asperisk.service.mapper.TarjetaMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Tarjeta.
 */
@RestController
@RequestMapping("/api")
public class TarjetaResource {

    private final Logger log = LoggerFactory.getLogger(TarjetaResource.class);

    private static final String ENTITY_NAME = "tarjeta";

    private final TarjetaRepository tarjetaRepository;

    private final TarjetaMapper tarjetaMapper;

    public TarjetaResource(TarjetaRepository tarjetaRepository, TarjetaMapper tarjetaMapper) {
        this.tarjetaRepository = tarjetaRepository;
        this.tarjetaMapper = tarjetaMapper;
    }

    /**
     * POST  /tarjetas : Create a new tarjeta.
     *
     * @param tarjetaDTO the tarjetaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tarjetaDTO, or with status 400 (Bad Request) if the tarjeta has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tarjetas")
    @Timed
    public ResponseEntity<TarjetaDTO> createTarjeta(@Valid @RequestBody TarjetaDTO tarjetaDTO) throws URISyntaxException {
        log.debug("REST request to save Tarjeta : {}", tarjetaDTO);
        if (tarjetaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tarjeta cannot already have an ID")).body(null);
        }
        Tarjeta tarjeta = tarjetaMapper.toEntity(tarjetaDTO);
        tarjeta = tarjetaRepository.save(tarjeta);
        TarjetaDTO result = tarjetaMapper.toDto(tarjeta);
        return ResponseEntity.created(new URI("/api/tarjetas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tarjetas : Updates an existing tarjeta.
     *
     * @param tarjetaDTO the tarjetaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tarjetaDTO,
     * or with status 400 (Bad Request) if the tarjetaDTO is not valid,
     * or with status 500 (Internal Server Error) if the tarjetaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tarjetas")
    @Timed
    public ResponseEntity<TarjetaDTO> updateTarjeta(@Valid @RequestBody TarjetaDTO tarjetaDTO) throws URISyntaxException {
        log.debug("REST request to update Tarjeta : {}", tarjetaDTO);
        if (tarjetaDTO.getId() == null) {
            return createTarjeta(tarjetaDTO);
        }
        Tarjeta tarjeta = tarjetaMapper.toEntity(tarjetaDTO);
        tarjeta = tarjetaRepository.save(tarjeta);
        TarjetaDTO result = tarjetaMapper.toDto(tarjeta);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tarjetaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tarjetas : get all the tarjetas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tarjetas in body
     */
    @GetMapping("/tarjetas")
    @Timed
    public List<TarjetaDTO> getAllTarjetas() {
        log.debug("REST request to get all Tarjetas");
        List<Tarjeta> tarjetas = tarjetaRepository.findAll();
        return tarjetaMapper.toDto(tarjetas);
    }

    /**
     * GET  /tarjetas/:id : get the "id" tarjeta.
     *
     * @param id the id of the tarjetaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tarjetaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tarjetas/{id}")
    @Timed
    public ResponseEntity<TarjetaDTO> getTarjeta(@PathVariable Long id) {
        log.debug("REST request to get Tarjeta : {}", id);
        Tarjeta tarjeta = tarjetaRepository.findOne(id);
        TarjetaDTO tarjetaDTO = tarjetaMapper.toDto(tarjeta);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tarjetaDTO));
    }

    /**
     * DELETE  /tarjetas/:id : delete the "id" tarjeta.
     *
     * @param id the id of the tarjetaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tarjetas/{id}")
    @Timed
    public ResponseEntity<Void> deleteTarjeta(@PathVariable Long id) {
        log.debug("REST request to delete Tarjeta : {}", id);
        tarjetaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
