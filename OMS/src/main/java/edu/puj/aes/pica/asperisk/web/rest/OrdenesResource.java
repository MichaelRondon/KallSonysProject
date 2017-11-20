package edu.puj.aes.pica.asperisk.web.rest;

import edu.puj.aes.pica.asperisk.oms.utilities.rest.util.PaginationUtil;
import edu.puj.aes.pica.asperisk.product.service.client.OrderServiceRestClient;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author acost
 */
@RestController
@RequestMapping("/api")
public class OrdenesResource {

    private final Logger log = LoggerFactory.getLogger(OrdenesResource.class);

    @Autowired
    private OrderServiceRestClient orderServiceRestClient;

    @GetMapping("/ordenes/rankingClientes")
    public ResponseEntity<List<Object>> getRankigCliente(@ApiParam Pageable pageable,
            @RequestParam(value = "idProducto", required = true) Long idProducto) {
        long initTime = System.currentTimeMillis();
        log.debug("REST request to get /ordenes/rankingClientes idProducto: {}", idProducto);
        Page<Object> page = orderServiceRestClient.rankingClientes(pageable, idProducto);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ordenes/rankingClientes");
        ResponseEntity<List<Object>> response = new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        log.debug("Fin get /ordenes/rankingClientes Tiempo: {}", (System.currentTimeMillis() - initTime));
        return response;
    }

    @GetMapping("/ordenes")
    public ResponseEntity<List<Object>> getOrdenes(@ApiParam Pageable pageable,
            @RequestParam(value = "idProducto", required = true) Long idProducto) {
        long initTime = System.currentTimeMillis();
        log.debug("REST request to get /ordenes idProducto: {}", idProducto);
        Page<Object> page = orderServiceRestClient.rankingOrdenes(pageable, idProducto);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ordenes");
        ResponseEntity<List<Object>> response = new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        log.debug("Fin get /ordenes Tiempo: {}", (System.currentTimeMillis() - initTime));
        return response;
    }

    @GetMapping("/ordenes/abiertas")
    public ResponseEntity<List<Object>> getOrdenesAbiertas(@ApiParam Pageable pageable) {
        long initTime = System.currentTimeMillis();
        log.debug("REST request to get /ordenes/abiertas");
        Page<Object> page = orderServiceRestClient.ordenesAbiertas(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ordenes/abiertas");
        ResponseEntity<List<Object>> response = new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        log.debug("Fin get /ordenes/abiertas Tiempo: {}", (System.currentTimeMillis() - initTime));
        return response;
    }

    @GetMapping("/ordenes/cerradas")
    public ResponseEntity<Object> getOrdenesCerradas(
            @RequestParam(value = "fecha", required = true) Instant fecha) {
        long initTime = System.currentTimeMillis();
        log.debug("REST request to get /ordenes/cerradas fecha: {}", fecha);
        Object object = orderServiceRestClient.ordenesCerradas(fecha);
        ResponseEntity<Object> ordenesCerradas = ResponseUtil.wrapOrNotFound(Optional.ofNullable(object));
        log.debug("Fin get /ordenes/cerradas Tiempo: {}", (System.currentTimeMillis() - initTime));
        return ordenesCerradas;
    }

    @GetMapping("/ordenes/rankingOrdenesFechas")
    public ResponseEntity<List<Object>> getOrdenesCerradas(@ApiParam Pageable pageable,
            @RequestParam(value = "fechaInicio", required = true) Instant fechaInicio,
            @RequestParam(value = "fechaFin", required = true) Instant fechaFin) {
        long initTime = System.currentTimeMillis();
        log.debug("REST request to get /ordenes/rankingOrdenesFechas fechaInicio: {}. fechaFin: {}", fechaInicio, fechaFin);
        Page<Object> rankingOrdenesCerradas = orderServiceRestClient.rankingOrdenesCerradas(pageable, fechaInicio, fechaFin);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(rankingOrdenesCerradas, "/api/ordenes/rankingOrdenesFechas");
        ResponseEntity<List<Object>> response = new ResponseEntity<>(rankingOrdenesCerradas.getContent(), headers, HttpStatus.OK);
        log.debug("Fin get /ordenes/rankingOrdenesFechas Tiempo: {}", (System.currentTimeMillis() - initTime));
        return response;
    }

    @GetMapping("/ordenes/rankingClientesFechas")
    public ResponseEntity<List<Object>> getRankingClientes(@ApiParam Pageable pageable,
            @RequestParam(value = "fechaInicio", required = true) Instant fechaInicio,
            @RequestParam(value = "fechaFin", required = true) Instant fechaFin) {
        long initTime = System.currentTimeMillis();
        log.debug("REST request to get /ordenes/rankingClientesFechas fechaInicio: {}. fechaFin: {}", fechaInicio, fechaFin);
        Page<Object> rankingOrdenesCerradas = orderServiceRestClient.rankingClientes(pageable, fechaInicio, fechaFin);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(rankingOrdenesCerradas, "/api/ordenes/rankingClientes");
        ResponseEntity<List<Object>> response = new ResponseEntity<>(rankingOrdenesCerradas.getContent(), headers, HttpStatus.OK);
        log.debug("Fin get /ordenes/rankingClientesFechas Tiempo: {}", (System.currentTimeMillis() - initTime));
        return response;
    }

    @GetMapping("/ordenes/total/{id}")
    public ResponseEntity<Object> getTotalOrden(@PathVariable Long id) {
        long initTime = System.currentTimeMillis();
        log.debug("REST request to get /ordenes/total/id id: {}", id);
        Object object = orderServiceRestClient.findOrdenTotal(id);
        ResponseEntity<Object> orden = ResponseUtil.wrapOrNotFound(Optional.ofNullable(object));
        log.debug("Fin get /ordenes/total/id Tiempo: {}", (System.currentTimeMillis() - initTime));
        return orden;
    }

    @GetMapping("/ordenes/detalle/{id}")
    public ResponseEntity<List<Object>> getDetalleOrden(@ApiParam Pageable pageable, @PathVariable Long id) {
        long initTime = System.currentTimeMillis();
        log.debug("REST request to get /ordenes/detalle/id id: {}", id);
        Page<Object> findOrdenDetalle = orderServiceRestClient.findOrdenDetalle(pageable, id);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(findOrdenDetalle, "/api/ordenes/detalle");
        ResponseEntity<List<Object>> response = new ResponseEntity<>(findOrdenDetalle.getContent(), headers, HttpStatus.OK);
        log.debug("Fin get /ordenes/detalle/id Tiempo: {}", (System.currentTimeMillis() - initTime));
        return response;
    }
}
