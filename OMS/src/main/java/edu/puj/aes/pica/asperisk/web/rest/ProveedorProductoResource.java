package edu.puj.aes.pica.asperisk.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.puj.aes.pica.asperisk.service.ProveedorProductoService;
import edu.puj.aes.pica.asperisk.oms.utilities.rest.util.HeaderUtil;
import edu.puj.aes.pica.asperisk.oms.utilities.dto.ProveedorProductoDTO;
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
 * REST controller for managing ProveedorProducto.
 */
@RestController
@RequestMapping("/api")
public class ProveedorProductoResource {

    private final Logger log = LoggerFactory.getLogger(ProveedorProductoResource.class);

    private static final String ENTITY_NAME = "proveedorProducto";

    private final ProveedorProductoService proveedorProductoService;

    public ProveedorProductoResource(ProveedorProductoService proveedorProductoService) {
        this.proveedorProductoService = proveedorProductoService;
    }

    /**
     * POST  /proveedor-productos : Create a new proveedorProducto.
     *
     * @param proveedorProductoDTO the proveedorProductoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new proveedorProductoDTO, or with status 400 (Bad Request) if the proveedorProducto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/proveedor-productos")
    @Timed
    public ResponseEntity<ProveedorProductoDTO> createProveedorProducto(@RequestBody ProveedorProductoDTO proveedorProductoDTO) throws URISyntaxException {
        log.debug("REST request to save ProveedorProducto : {}", proveedorProductoDTO);
        if (proveedorProductoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new proveedorProducto cannot already have an ID")).body(null);
        }
        ProveedorProductoDTO result = proveedorProductoService.save(proveedorProductoDTO);
        return ResponseEntity.created(new URI("/api/proveedor-productos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /proveedor-productos : Updates an existing proveedorProducto.
     *
     * @param proveedorProductoDTO the proveedorProductoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated proveedorProductoDTO,
     * or with status 400 (Bad Request) if the proveedorProductoDTO is not valid,
     * or with status 500 (Internal Server Error) if the proveedorProductoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/proveedor-productos")
    @Timed
    public ResponseEntity<ProveedorProductoDTO> updateProveedorProducto(@RequestBody ProveedorProductoDTO proveedorProductoDTO) throws URISyntaxException {
        log.debug("REST request to update ProveedorProducto : {}", proveedorProductoDTO);
        if (proveedorProductoDTO.getId() == null) {
            return createProveedorProducto(proveedorProductoDTO);
        }
        ProveedorProductoDTO result = proveedorProductoService.save(proveedorProductoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, proveedorProductoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /proveedor-productos : get all the proveedorProductos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of proveedorProductos in body
     */
    @GetMapping("/proveedor-productos")
    @Timed
    public List<ProveedorProductoDTO> getAllProveedorProductos() {
        log.debug("REST request to get all ProveedorProductos");
        return proveedorProductoService.findAll();
    }

    /**
     * GET  /proveedor-productos/:id : get the "id" proveedorProducto.
     *
     * @param id the id of the proveedorProductoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the proveedorProductoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/proveedor-productos/{id}")
    @Timed
    public ResponseEntity<ProveedorProductoDTO> getProveedorProducto(@PathVariable Long id) {
        log.debug("REST request to get ProveedorProducto : {}", id);
        ProveedorProductoDTO proveedorProductoDTO = proveedorProductoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(proveedorProductoDTO));
    }

    /**
     * DELETE  /proveedor-productos/:id : delete the "id" proveedorProducto.
     *
     * @param id the id of the proveedorProductoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/proveedor-productos/{id}")
    @Timed
    public ResponseEntity<Void> deleteProveedorProducto(@PathVariable Long id) {
        log.debug("REST request to delete ProveedorProducto : {}", id);
        proveedorProductoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
