/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.puj.aes.pica.asperisk.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.puj.aes.pica.asperisk.service.ClientService;
import io.github.jhipster.web.util.ResponseUtil;
import java.util.Optional;
import java.util.logging.Level;
import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 *
* REST controller for managing Clientes.
 */

@RestController
@RequestMapping("/api")
public class ClienteResource {
    
    private final Logger log = LoggerFactory.getLogger(ClienteResource.class);
    private final ClientService clientService;

    public ClienteResource(ClientService clientService) {
        this.clientService = clientService;
    }
    
    @GetMapping("/clientes/{id}")
    @Timed
    public ResponseEntity<String> getCliente(@PathVariable String id) {
        log.info("REST request to get Cliente : {}", id);
        return  new ResponseEntity<>(clientService.find(id), HttpStatus.OK);
    }    
    
    @PostMapping("/clientes")
    @Timed
    public ResponseEntity<String> createCliente (@Valid @RequestBody String cliente) {
        log.info("REST! request to create Cliente : {}", cliente);
        return  new ResponseEntity<>(clientService.create(cliente), HttpStatus.CREATED);
    }    
 
    @PutMapping("/clientes")
    @Timed
    public ResponseEntity<String> updateCliente(@Valid @RequestBody String cliente) {
        log.info("REST request to update Cliente : {}", cliente);
        return  new ResponseEntity<>(clientService.update(cliente), HttpStatus.OK);
    }
}
