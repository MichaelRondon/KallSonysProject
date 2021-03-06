package edu.puj.aes.pica.asperisk.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.puj.aes.pica.asperisk.oms.utilities.rest.util.HeaderUtil;
import edu.puj.aes.pica.asperisk.oms.utilities.rest.util.PaginationUtil;
import edu.puj.aes.pica.asperisk.service.ClienteService;
import edu.puj.aes.pica.asperisk.service.dto.ClienteDTO;
import edu.puj.aes.pica.asperisk.oms.utilities.rest.util.errors.CustomParameterizedException;
import io.swagger.annotations.ApiParam;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.Map;

/**
 * REST controller for managing Cliente.
 */
@RestController
@RequestMapping("/api")
public class ClienteResource {

    private final Logger log = LoggerFactory.getLogger(ClienteResource.class);

    private static final String ENTITY_NAME = "cliente";

    private final ClienteService clienteService;

    public ClienteResource(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

//    /**
//     * POST  /clientes : Create a new cliente.
//     *
//     * @param clienteDTO the clienteDTO to create
//     * @return the ResponseEntity with status 201 (Created) and with body the new clienteDTO, or with status 400 (Bad Request) if the cliente has already an ID
//     * @throws URISyntaxException if the Location URI syntax is incorrect
//     */
//    @PostMapping("/clientes")
//    @Timed
//    public ResponseEntity<ClienteDTO> createCliente(@Valid @RequestBody ClienteDTO clienteDTO) throws URISyntaxException {
//        log.debug("REST request to save Cliente : {}", clienteDTO);
//        if (clienteDTO.getId() != null) {
//            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new cliente cannot already have an ID")).body(null);
//        }
//        ClienteDTO result = clienteService.save(clienteDTO);
//        return ResponseEntity.created(new URI("/api/clientes/" + result.getId()))
//            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
//            .body(result);
//    }
    @PostMapping("/clientes")
    @Timed
    public ResponseEntity<String> createCliente(@Valid @RequestBody String cliente) throws URISyntaxException {
        log.info("REST! request to create Cliente : {}", cliente);
        String create;
        Map<String, Object> map;
        try {
            create = clienteService.create(cliente);
            ObjectMapper mapper = new ObjectMapper();
            map = mapper.readValue(cliente, new TypeReference<Map<String, Object>>() {
            });
        } catch (CustomParameterizedException | IOException cpe) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, cpe.getMessage(), "")).body(null);
        }

        return ResponseEntity.created(new URI("/api/clientes/" + map.get("documento")))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, map.get("documento").toString()))
                .body(create);
    }

//    /**
//     * PUT  /clientes : Updates an existing cliente.
//     *
//     * @param clienteDTO the clienteDTO to update
//     * @return the ResponseEntity with status 200 (OK) and with body the updated clienteDTO,
//     * or with status 400 (Bad Request) if the clienteDTO is not valid,
//     * or with status 500 (Internal Server Error) if the clienteDTO couldn't be updated
//     * @throws URISyntaxException if the Location URI syntax is incorrect
//     */
//    @PutMapping("/clientes")
//    @Timed
//    public ResponseEntity<ClienteDTO> updateCliente(@Valid @RequestBody ClienteDTO clienteDTO) throws URISyntaxException {
//        log.debug("REST request to update Cliente : {}", clienteDTO);
//        if (clienteDTO.getId() == null) {
//            return createCliente(clienteDTO);
//        }
//        ClienteDTO result = clienteService.save(clienteDTO);
//        return ResponseEntity.ok()
//            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clienteDTO.getId().toString()))
//            .body(result);
//    }
    @PutMapping("/clientes")
    @Timed
    public ResponseEntity<String> updateCliente(@Valid @RequestBody String cliente) throws URISyntaxException {
        log.info("REST request to update Cliente : {}", cliente);
        String update;
        Map<String, Object> map;
        try {
            update = clienteService.update(cliente);
            ObjectMapper mapper = new ObjectMapper();
            map = mapper.readValue(cliente, new TypeReference<Map<String, Object>>() {
            });
        } catch (CustomParameterizedException | IOException cpe) {
            log.error("Error al actualizar el cliente: {}", cpe.getMessage());
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, cpe.getMessage(), "")).body(null);
        }
        return ResponseEntity.created(new URI("/api/clientes/" + map.get("documento")))
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, map.get("documento").toString()))
                .body(update);
    }

    /**
     * GET /clientes : get all the clientes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of clientes
     * in body
     */
    @GetMapping("/clientes")
    @Timed
    public ResponseEntity<List<ClienteDTO>> getAllClientes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Clientes");
        Page<ClienteDTO> page = clienteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/clientes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /clientes/:id : get the "id" cliente.
     *
     * @param id the id of the clienteDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * clienteDTO, or with status 404 (Not Found)
     */
//    @GetMapping("/clientes/{id}")
//    @Timed
//    public ResponseEntity<ClienteDTO> getCliente(@PathVariable Long id) {
//        log.debug("REST request to get Cliente : {}", id);
//        ClienteDTO clienteDTO = clienteService.findOne(id);
//        if(clienteDTO == null){
//            throw new CustomParameterizedException("No encontrado");
//        }
//        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(clienteDTO));
//    }
    @GetMapping("/clientes/{id}")
    @Timed
    public ResponseEntity<String> getCliente(@PathVariable String id) {
        log.info("REST request to get Cliente : {}", id);
        String find = clienteService.find(id);
        log.info("find : {}", find);
        return new ResponseEntity<>(find, HttpStatus.OK);
    }

    /**
     * DELETE /clientes/:id : delete the "id" cliente.
     *
     * @param id the id of the clienteDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/clientes/{id}")
    @Timed
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        log.debug("REST request to delete Cliente : {}", id);
        clienteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
