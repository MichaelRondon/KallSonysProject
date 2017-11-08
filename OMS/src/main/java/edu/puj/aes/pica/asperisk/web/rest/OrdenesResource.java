package edu.puj.aes.pica.asperisk.web.rest;

import edu.puj.aes.pica.asperisk.oms.utilities.rest.util.PaginationUtil;
import edu.puj.aes.pica.asperisk.product.service.client.OrderServiceRestClient;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<List<Object>> getProducto(@ApiParam Pageable pageable,
            @RequestParam(value = "idProducto", required = false) Long idProducto) {
        log.debug("REST request to get /ordenes/rankingClientes idProducto: {}", idProducto);
        Page<Object> page = orderServiceRestClient.rankingClientes(pageable, idProducto);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ordenes/rankingClientes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
