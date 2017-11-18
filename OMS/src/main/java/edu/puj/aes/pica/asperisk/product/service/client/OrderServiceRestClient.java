/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.puj.aes.pica.asperisk.product.service.client;

import java.time.Instant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author acost
 */
public interface OrderServiceRestClient {
    
    Page<Object> rankingClientes(Pageable pageable, Long idProducto);
    Page<Object> rankingOrdenes(Pageable pageable, Long idProducto);
    Page<Object> ordenesAbiertas(Pageable pageable);
    Object ordenesCerradas(Instant fecha);
}
